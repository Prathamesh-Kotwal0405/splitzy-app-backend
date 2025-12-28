package com.splitzy.appli.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.splitzy.appli.entities.ExpenseEntity;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
    
    
} 