package com.splitzy.appli.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.splitzy.appli.entities.ExpenseSplitEntity;

public interface ExpenseSplitRepository extends JpaRepository<ExpenseSplitEntity, Long>  {
    
    @Query("""
            select es from ExpenseSplitEntity es where es.expense.group.id = :groupId
            """)
        List<ExpenseSplitEntity> findByGroupId(@Param("groupId") Long groupId);
}
