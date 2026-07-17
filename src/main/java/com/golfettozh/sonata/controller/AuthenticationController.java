package com.golfettozh.sonata.controller;

import com.golfettozh.sonata.dto.request.AuthenticationRequestDTO;
import com.golfettozh.sonata.dto.request.RegisterRequestDTO;
import com.golfettozh.sonata.model.user.User;
import com.golfettozh.sonata.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<HttpHeaders> login(@RequestBody @Valid AuthenticationRequestDTO data) {
        var emailPassword = new UsernamePasswordAuthenticationToken(
                data.email(),
                data.password()
        );

        var authentication = this.authenticationManager.authenticate(emailPassword);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequestDTO data) {
        if (userRepository.findByEmail(data.email()) != null) {
            return ResponseEntity.badRequest().body("Email already in use");
        }
        
        String encryptedPassword = passwordEncoder.encode(data.password());
        User newUser = new User(data.email(), encryptedPassword, data.role());

        return ResponseEntity.ok().build();
    }

}
