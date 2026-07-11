package com.golfettozh.sonata.service;

import com.golfettozh.sonata.dto.request.UserRequestDTO;
import com.golfettozh.sonata.model.User;
import com.golfettozh.sonata.repository.UserRepository;
import com.golfettozh.sonata.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(UserRequestDTO dto) {
        Objects.requireNonNull(dto, "Dados do usuário são obrigatórios");

        User user = new User();

        user.setUsername(dto.getUsername().trim());
        user.setEmail(dto.getEmail().trim().toLowerCase());
        user.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt())); // TODO: Hash the password before saving (BCrypt)
        System.out.println(user.getPassword()); // Print the hashed password for debugging purposes
        return userRepository.save(user);
    }

    // No seu UserService.java:
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + id));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User deleteById(Long id) {
        User user = findById(id);

        userRepository.delete(user);

        return user;
    }
}
