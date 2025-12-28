package com.splitzy.appli.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.splitzy.appli.entities.SettlementEntity;

public interface SettlementRepository extends JpaRepository<SettlementEntity, Long>{
    
    @Query("""
            select s from SettlementEntity s where s.group.id = :groupId
            """)
    List<SettlementEntity> findByGroupId(@Param("groupId") Long groupId);
}
