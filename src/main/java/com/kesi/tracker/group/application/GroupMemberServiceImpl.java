package com.kesi.tracker.group.application;

import com.kesi.tracker.group.application.repository.GroupMemberRepository;
import com.kesi.tracker.group.domain.GroupMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupMemberServiceImpl implements GroupMemberService {
    private final GroupMemberRepository groupMemberRepository;

    @Override
    public GroupMember getApprovedByGidAndUid(Long gid, Long uid) {
        GroupMember groupMember = getByGidAndUid(gid, uid);

        if(!groupMember.isApproved())
            throw new RuntimeException("Group member is not approved");

        return groupMember;
    }

    @Override
    public GroupMember getByGidAndUid(Long gid, Long uid) {
        return groupMemberRepository.findByGidAndUid(gid, uid)
                .orElseThrow(() -> new RuntimeException("Group member not found"));
    }
}
