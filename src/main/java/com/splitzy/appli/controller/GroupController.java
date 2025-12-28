package com.splitzy.appli.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.splitzy.appli.dto.request.AddGroupMemberRequest;
import com.splitzy.appli.dto.request.CreateGroupRequest;
import com.splitzy.appli.dto.response.GroupResponse;
import com.splitzy.appli.service.GroupService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<GroupResponse> createGroup(
            @Valid @RequestBody CreateGroupRequest request) {
        return ResponseEntity.ok(groupService.createGroup(request));
    }

    @PostMapping("/{groupId}/members")
    public ResponseEntity<String> addMember(
            @PathVariable Long groupId,
            @Valid @RequestBody AddGroupMemberRequest request) {
        groupService.addMember(groupId, request);
        return ResponseEntity.ok("Member added successfully");
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> myGroups() {
        return ResponseEntity.ok(groupService.getMyGroups());
    }
}

