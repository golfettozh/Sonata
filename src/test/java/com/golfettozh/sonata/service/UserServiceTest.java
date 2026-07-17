package com.golfettozh.sonata.service;

import com.golfettozh.sonata.dto.request.UserRequestDTO;
import com.golfettozh.sonata.exception.ResourceNotFoundException;
import com.golfettozh.sonata.model.user.User;
import com.golfettozh.sonata.model.user.UserRole;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserServiceTest {

    private final UserService userService;
    private final EntityManager entityManager;

    @Autowired
    public UserServiceTest(UserService userService, EntityManager entityManager) {
        this.userService = userService;
        this.entityManager = entityManager;
    }

    @Test
    @DisplayName("Should return user info if user exists and has a valid ID")
    void shouldReturnUserInfoByIdWhenExist() {
        UserRequestDTO dto = new UserRequestDTO("teste@gmail.com", "1234567890", UserRole.ROLE_USER);
        User savedUser = this.createUser(dto);

        User result = userService.findById(savedUser.getId());

        assertNotNull(result);
        assertEquals(savedUser.getId(), result.getId());
        System.out.println(savedUser.getId());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException if user does not exist")
    void shouldThrowResourceNotFoundExceptionWhenUserDoesNotExist() {
        UUID idNonexistent  = UUID.randomUUID();

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> userService.findById(idNonexistent)
        );

        assertEquals(String.valueOf(idNonexistent), exception.getMessage());
    }

    private User createUser(UserRequestDTO userRequestDTO) {
        User newUser = new User();
        newUser.setEmail(userRequestDTO.email());
        newUser.setPassword(userRequestDTO.password());

        this.entityManager.persist(newUser);
        this.entityManager.flush();
        this.entityManager.clear();

        return newUser;
    }
}