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

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
        User user = userService.findById(id);

        UserResponseDTO response = new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<User> users = userService.findAll();
        List<UserResponseDTO> responses = users.stream().map(user -> new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        )).toList();

        return ResponseEntity.ok(responses);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponseDTO> deleteUser(@Valid @PathVariable Long id) {
        User deletedUser = userService.deleteById(id);
        UserResponseDTO response = new UserResponseDTO(
                deletedUser.getId(),
                deletedUser.getUsername(),
                deletedUser.getEmail()
        );
        return ResponseEntity.ok(response);
    }
}
