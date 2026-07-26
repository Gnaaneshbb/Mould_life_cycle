package com.example.repository;

import com.example.entity.ShiftEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ShiftEntryRepository extends JpaRepository<ShiftEntry, Long> {

    List<ShiftEntry> findByMouldIdOrderByTimestampDesc(String mouldId);
    
    List<ShiftEntry> findByDateAndShiftOrderByTimestampAsc(
            LocalDate date,
            String shift
    );

}