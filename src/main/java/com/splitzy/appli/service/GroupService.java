package com.splitzy.appli.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.splitzy.appli.dto.request.AddGroupMemberRequest;
import com.splitzy.appli.dto.request.CreateGroupRequest;
import com.splitzy.appli.dto.response.GroupResponse;
import com.splitzy.appli.entities.GroupMemberEntity;
import com.splitzy.appli.entities.UserEntity;
import com.splitzy.appli.entities.UserGroupEntity;
import com.splitzy.appli.exception.BadRequestException;
import com.splitzy.appli.exception.ResourceNotFoundException;
import com.splitzy.appli.repository.GroupMemberRepository;
import com.splitzy.appli.repository.GroupRepository;
import com.splitzy.appli.repository.UserRepository;
import com.splitzy.appli.util.SecurityUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final UserRepository userRepository;

    @Transactional
    public GroupResponse createGroup(CreateGroupRequest request) {

        UserEntity currentUser = SecurityUtil.getCurrentUser();

        UserGroupEntity group = new UserGroupEntity();
        group.setName(request.getName());
        group.setCreatedBy(currentUser);

        UserGroupEntity savedGroup = groupRepository.save(group);

        GroupMemberEntity member = new GroupMemberEntity();
        member.setGroup(savedGroup);
        member.setUser(currentUser);
        groupMemberRepository.save(member);

        return new GroupResponse(savedGroup.getId(), savedGroup.getName());
    }

    @Transactional
    public void addMember(Long groupId, AddGroupMemberRequest request) {

        UserEntity currentUser = SecurityUtil.getCurrentUser();

        UserGroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        UserEntity userToAdd = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (groupMemberRepository.existsByGroupAndUser(group, userToAdd)) {
            throw new BadRequestException("User already in group");
        }

        GroupMemberEntity member = new GroupMemberEntity();
        member.setGroup(group);
        member.setUser(userToAdd);
        groupMemberRepository.save(member);
    }

    public List<GroupResponse> getMyGroups() {

        UserEntity currentUser = SecurityUtil.getCurrentUser();

        return groupMemberRepository.findGroupsByUserId(currentUser.getId())
                .stream()
                .map(g -> new GroupResponse(g.getId(), g.getName()))
                .toList();
    }
}
