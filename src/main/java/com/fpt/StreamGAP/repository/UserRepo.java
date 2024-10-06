package com.fpt.StreamGAP.repository;

import com.fpt.StreamGAP.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {

    List<User> findByRole(String role);
    Optional<User> findByEmail(String email);
}
