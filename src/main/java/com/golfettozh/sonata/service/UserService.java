package com.golfettozh.sonata.service;

import com.golfettozh.sonata.dto.request.UserRequestDTO;
import com.golfettozh.sonata.model.user.User;
import com.golfettozh.sonata.repository.UserRepository;
import com.golfettozh.sonata.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(UserRequestDTO dto) {
        Objects.requireNonNull(dto, "Dado's do usuário são obrigatórios");

        User user = new User();

        user.setEmail(dto.email().toLowerCase());
        user.setPassword(BCrypt.hashpw(dto.password(), BCrypt.gensalt()));
        user.setRole(dto.role());

        return userRepository.save(user);
    }

    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString()));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteById(UUID id) {
        User user = findById(id);

        userRepository.delete(user);
    }
}
