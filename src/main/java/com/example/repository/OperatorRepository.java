package com.example.repository;

import com.example.entity.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OperatorRepository extends JpaRepository<Operator, Long> {

    Optional<Operator> findByUsername(String username);
    List<Operator> findByRoleIn(List<String> roles);

}