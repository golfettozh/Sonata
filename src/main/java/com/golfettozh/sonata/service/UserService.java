package com.golfettozh.sonata.service;

import com.golfettozh.sonata.dto.request.UserRequestDTO;
import com.golfettozh.sonata.dto.response.UserResponseDTO;
import com.golfettozh.sonata.model.User;
import com.golfettozh.sonata.repository.UserRepository;
import com.golfettozh.sonata.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
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
        user.setPassword(dto.getPassword()); // TODO: Hash the password before saving (BCrypt)

        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario não cadastrado ou não encontrado"));
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
