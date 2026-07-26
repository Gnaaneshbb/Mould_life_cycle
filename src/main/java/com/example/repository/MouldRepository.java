package com.example.repository;

import com.example.entity.Mould;
import com.example.enums.MouldStatus;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MouldRepository extends JpaRepository<Mould, String> {
	List<Mould> findByStatus(MouldStatus status);
	long countByTotalCyclesBetween(int start, int end);
	Optional<Mould> findById(String mouldId);
}
