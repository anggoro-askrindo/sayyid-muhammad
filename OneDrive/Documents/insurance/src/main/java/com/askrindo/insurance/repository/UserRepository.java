package com.askrindo.insurance.repository;

import com.askrindo.insurance.model.User;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    // find by username
    User findByUsername(String username);
}

