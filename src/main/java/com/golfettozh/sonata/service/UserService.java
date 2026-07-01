package com.golfettozh.sonata.service;

import com.golfettozh.sonata.dto.request.UserRequestDTO;
import com.golfettozh.sonata.model.User;
import com.golfettozh.sonata.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(UserRequestDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());

        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário com o ID: " + id + ", não foi encontrado."));
    }
}
