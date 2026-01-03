package com.splitzy.appli.repository;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.splitzy.appli.entities.ExpenseEntity;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
    
    List<ExpenseEntity> findByGroupIdOrderByCreatedAtDesc(Long groupId);

    List<ExpenseEntity> findByPaidByIdOrderByCreatedAtDesc(Long userId);

    @Query("""
            select distinct es.expense
            from ExpenseSplitEntity es
            where es.user.id = :userId
            order by es.expense.createdAt desc
            """)
    List<ExpenseEntity> findExpensesInvolvingUser(@Param("userId") Long userId);

    Page<ExpenseEntity> findByGroupId(Long groupId, Pageable pageable);
    Page<ExpenseEntity> findByPaidById(Long userId, Pageable pageable);



} 