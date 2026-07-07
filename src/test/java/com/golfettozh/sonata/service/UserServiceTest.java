package com.golfettozh.sonata.service;

import com.golfettozh.sonata.dto.request.UserRequestDTO;
import com.golfettozh.sonata.model.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

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
    @DisplayName("Deve retornar o usuário do banco através do Service quando o ID existir")
    void findById() {
        UserRequestDTO dto = new UserRequestDTO("testeuser", "teste@gmail.com", "1234567890");
        User savedUser = this.createUser(dto);

        User result = userService.findById(savedUser.getId());

        assertNotNull(result);
        assertEquals(savedUser.getId(), result.getId());
    }

    private User createUser(UserRequestDTO userRequestDTO) {
        User newUser = new User();
        newUser.setUsername(userRequestDTO.getUsername());
        newUser.setEmail(userRequestDTO.getEmail());
        newUser.setPassword(userRequestDTO.getPassword());

        this.entityManager.persist(newUser);
        this.entityManager.flush();
        return newUser;
    }
}