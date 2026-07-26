package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.DeletedMould;

public interface DeletedMouldRepository extends JpaRepository<DeletedMould,String>{

}