package com.example.pal.service;

import com.example.pal.dto.ExamSubmissionDTO;
import com.example.pal.dto.UserDTO;
import com.example.pal.model.Exam;
import com.example.pal.model.ExamAttempt;
import com.example.pal.model.User;
import com.example.pal.repository.ExamAttemptRepository;
import com.example.pal.repository.ExamRepository;
import com.example.pal.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class ExamAttemptService {
    
    @Autowired
    private ExamAttemptRepository examAttemptRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private ModelMapper modelMapper;

    private double calculateScore(TreeMap<Integer, String> submittedAnswers, TreeMap<Integer, String> correctAnswers){
        if (submittedAnswers == null || correctAnswers == null || correctAnswers.isEmpty()){
            return 0.0;
        }

        int totalQuestions = correctAnswers.size();
        int correctCount = 0;

        for(Integer questionNumber : correctAnswers.keySet()){
            String correctAnswer = correctAnswers.get(questionNumber);
            String submittedAnswer = submittedAnswers.get(questionNumber);

            if (correctAnswer != null && correctAnswer.equalsIgnoreCase(submittedAnswer)){
                correctCount++;
            }
        }

        return (double) correctCount / totalQuestions * 100;
    }

    public ExamAttempt registerAttempt(ExamSubmissionDTO submissionDTO, Long examId){
        User user = userRepository.findById(submissionDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + submissionDTO.getUserId()));

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new EntityNotFoundException("Examen no encontrado con id: " + examId));

        TreeMap<Integer, String> correctAnswers = exam.getCorrectAnswersMap();
        TreeMap<Integer, String> submittedAnswers = new TreeMap<>(submissionDTO.getAnswersMap());

        double score = calculateScore(submittedAnswers, correctAnswers);

        ExamAttempt attempt = new ExamAttempt();
        attempt.setExam(exam);
        attempt.setStudent(user);
        attempt.setScore(score);
        attempt.setAttemptDate(LocalDateTime.now());
        attempt.setAnswersMap(new TreeMap<>(submittedAnswers));

        return examAttemptRepository.save(attempt);
    }

    public List<ExamSubmissionDTO> getExamResult(Long examId) {
        Optional<ExamAttempt> attemptsList = examAttemptRepository.findById(examId);
        return attemptsList.stream().map(attempt->modelMapper.map(attempt, ExamSubmissionDTO.class)).collect(Collectors.toList());

    }

}
