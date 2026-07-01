package com.golfettozh.sonata.repository;

import com.golfettozh.sonata.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
