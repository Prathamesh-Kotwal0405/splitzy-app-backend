package com.splitzy.appli.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.splitzy.appli.entities.GroupMemberEntity;
import com.splitzy.appli.entities.UserEntity;
import com.splitzy.appli.entities.UserGroupEntity;

public interface GroupMemberRepository extends JpaRepository<GroupMemberEntity, Long> {

    boolean existsByGroupAndUser(UserGroupEntity group, UserEntity user);

    @Query("""
        select gm.group
        from GroupMemberEntity gm
        where gm.user.id = :userId
    """)
    List<UserGroupEntity> findGroupsByUserId(Long userId);
    
}
