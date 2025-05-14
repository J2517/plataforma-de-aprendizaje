package com.example.pal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pal.dto.PaymentDTO;
import com.example.pal.model.Payment;
import com.example.pal.service.PaymentService;



@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<Payment> createPayment(@RequestBody PaymentDTO paymentDTO) {
        Payment payment = paymentService.createPayment(paymentDTO);
        return ResponseEntity.status(201).body(payment);
    }

}
