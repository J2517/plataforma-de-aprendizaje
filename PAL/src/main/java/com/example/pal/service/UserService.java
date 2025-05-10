package com.example.pal.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.pal.dto.CreateUserDTO;
import com.example.pal.dto.UserDTO;
import com.example.pal.dto.UpdateUserDTO;
import com.example.pal.model.Role;
import com.example.pal.model.User;
import com.example.pal.repository.RoleRepository;
import com.example.pal.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired //instancia de modelMapper
    private ModelMapper modelMapper;

    public User createUserWithRoles(CreateUserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Set<Role> roles = new HashSet<>();
        for (String roleName : userDTO.getRoles()) {
            String normalizedRoleName = roleName.trim().toLowerCase(); // Normaliza el nombre del rol
            Role role = roleRepository.findByName(normalizedRoleName)
                    .orElseGet(() -> {
                        Role newRole = new Role();
                        newRole.setName(normalizedRoleName); // Asigna un String simple
                        return roleRepository.save(newRole);
                    });
            roles.add(role);
        }

        user.setRoles(roles);
        return userRepository.save(user);
    } 

    
    public List<UserDTO> getAllUsers() {
    	List<User> users = userRepository.findAll();
    	return users.stream().map(user->modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
    			
    }
    
    public Optional<User> getUserById(Long id){
    	return userRepository.findById(id);
    }

    public User updateUser(Long id, UpdateUserDTO userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        if (userDetails.getUsername() != null) {
            user.setUsername(userDetails.getUsername());
        }

        // Convertir roles de String[] a Set<Role>
        /*
        if (userDetails.getRoles() != null) {
            Set<Role> roles = Arrays.stream(userDetails.getRoles())
                    .map(roleName -> roleRepository.findByName(roleName)
                            .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }*/

        return userRepository.save(user);
    }
    
    public void deleteUser(Long id) {
    	userRepository.deleteById(id);
    }
    public List<User> getUsersByRole(String roleName) {
        return userRepository.findUsersByRole(roleName);
    }
}