package com.kesi.tracker.group.application;

import com.kesi.tracker.group.application.repository.GroupMemberRepository;
import com.kesi.tracker.group.domain.GroupMember;
import com.kesi.tracker.group.domain.GroupRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
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

    @Override
    public List<GroupMember> findByGidAndRoleIsLeader(Long gid) {
        List<GroupMember> leaderGroupMembers = groupMemberRepository.findByGidAndRole(gid, GroupRole.LEADER);
        if(leaderGroupMembers.isEmpty()) {
            log.error("해당 그룹에 리더가 존재하지 않습니다..? groupId : {}", gid);
        }

        return leaderGroupMembers;
    }
}
