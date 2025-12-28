package com.splitzy.appli.service;

import org.springframework.stereotype.Service;

import com.splitzy.appli.dto.request.SettlementRequest;
import com.splitzy.appli.entities.SettlementEntity;
import com.splitzy.appli.entities.UserEntity;
import com.splitzy.appli.entities.UserGroupEntity;
import com.splitzy.appli.exception.BadRequestException;
import com.splitzy.appli.exception.ResourceNotFoundException;
import com.splitzy.appli.repository.GroupRepository;
import com.splitzy.appli.repository.SettlementRepository;
import com.splitzy.appli.repository.UserRepository;
import com.splitzy.appli.util.SecurityUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SettlementService {

    private final SettlementRepository settlementRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    
    @Transactional
    public void settle(SettlementRequest request){
        UserEntity fromUser = SecurityUtil.getCurrentUser();

        UserGroupEntity group = groupRepository.findById(request.getGroupId())
                        .orElseThrow(() -> new ResourceNotFoundException("GroupNotFound"));

        UserEntity toUser = userRepository.findById(request.getToUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (fromUser.getId().equals(toUser.getId())) {
            throw new BadRequestException("Cannot settle with yourself");
        }

        SettlementEntity settlement = new SettlementEntity();
        settlement.setGroup(group);
        settlement.setFromUser(fromUser);
        settlement.setToUser(toUser);
        settlement.setAmount(request.getAmount());

        settlementRepository.save(settlement);
    }

}
