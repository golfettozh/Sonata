package com.golfettozh.sonata.controller;

import com.golfettozh.sonata.dto.request.UserRequestDTO;
import com.golfettozh.sonata.dto.response.UserResponseDTO;
import com.golfettozh.sonata.model.User;
import com.golfettozh.sonata.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
        User user = userService.findById(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserResponseDTO response = new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userDTO) {

        User savedUser = userService.save(userDTO);

        UserResponseDTO response = new UserResponseDTO(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}