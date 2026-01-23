package com.kesi.tracker.track.application;

import com.kesi.tracker.group.application.GroupMemberService;
import com.kesi.tracker.group.domain.GroupMember;
import com.kesi.tracker.track.application.repository.TrackAssignmentRepository;
import com.kesi.tracker.track.application.repository.TrackMemberRepository;
import com.kesi.tracker.track.domain.Track;
import com.kesi.tracker.track.domain.TrackAssignment;
import com.kesi.tracker.track.domain.TrackMember;
import com.kesi.tracker.track.domain.event.TrackAppliedEvent;
import com.kesi.tracker.track.domain.event.TrackCanceledEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrackAssignmentServiceImpl implements TrackAssignmentService {
    private final TrackService trackService;
    private final GroupMemberService groupMemberService;
    private final TrackAssignmentRepository trackAssignmentRepository;
    private final TrackMemberRepository trackMemberRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    private TrackMember getTrackMemberByTrackAssignmentIdAndUid(Long trackAssignmentId, Long uid) {
        return trackMemberRepository.findByTrackAssignmentIdAndUid(trackAssignmentId, uid)
                .orElseThrow(() -> new RuntimeException("trackMember not found"));
    }
    @Override
    public void applyTrackAssignment(Long currentUid, Long trackAssignmentId) {
        TrackAssignment trackAssignment = getById(trackAssignmentId);
        if(!trackAssignment.isWithinPeriod(LocalDateTime.now()))
            throw new RuntimeException("수강 신청 기간이 아닙니다");

        Track track = trackService.getById(trackAssignment.getTrackId());

        GroupMember groupMember = groupMemberService.getApprovedByGidAndUid(track.getGid(), currentUid);
        if(!groupMember.isFollower())
            throw new IllegalStateException("follower만 신청 가능합니다");

        trackMemberRepository.save(TrackMember.builder()
                .uid(currentUid)
                .trackAssignmentId(trackAssignmentId)
                .createdAt(LocalDateTime.now())
                .build());

        List<GroupMember> leaderGroupMembers = groupMemberService.findByGidAndRoleIsLeader(groupMember.getGid());

        applicationEventPublisher.publishEvent(TrackAppliedEvent.builder()
                .appliedUserId(currentUid)
                .trackAssignmentId(trackAssignmentId)
                .groupId(groupMember.getGid())
                .hostId(track.getHostId())
                .groupLeaderIds(leaderGroupMembers.stream().map(GroupMember::getUid).collect(Collectors.toList()))
                .build());
    }

    @Override
    public void cancelTrackAssignment(Long currentUid, Long trackAssignmentId) {
        TrackMember trackMember = getTrackMemberByTrackAssignmentIdAndUid(trackAssignmentId, currentUid);
        TrackAssignment trackAssignment = getById(trackAssignmentId);
        Track track = trackService.getById(trackAssignment.getTrackId());

        trackMemberRepository.deleteById(trackMember.getId());
        List<GroupMember> leaderGroupMembers = groupMemberService.findByGidAndRoleIsLeader(track.getGid());

        applicationEventPublisher.publishEvent(TrackCanceledEvent.builder()
                .canceledUserId(currentUid)
                .trackAssignmentId(trackAssignmentId)
                .groupId(track.getGid())
                .hostId(track.getHostId())
                .groupLeaderIds(leaderGroupMembers.stream().map(GroupMember::getUid).collect(Collectors.toList()))
                .build());
    }

    @Override
    public TrackAssignment getById(Long trackAssignmentId) {
        return trackAssignmentRepository.findById(trackAssignmentId)
                .orElseThrow(() -> new IllegalArgumentException("TrackAssignment not found"));
    }

    @Override
    public TrackAssignment save(TrackAssignment trackAssignment) {
        return trackAssignmentRepository.save(trackAssignment);
    }

    @Override
    public void deleteById(Long trackAssignmentId) {
        trackAssignmentRepository.deleteById(trackAssignmentId);
    }
}
