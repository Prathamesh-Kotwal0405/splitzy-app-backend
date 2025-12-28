package com.splitzy.appli.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.splitzy.appli.entities.UserGroupEntity;

public interface GroupRepository extends JpaRepository<UserGroupEntity, Long> {

    
} 
