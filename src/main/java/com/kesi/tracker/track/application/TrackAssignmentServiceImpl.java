package com.kesi.tracker.track.application;

import com.kesi.tracker.group.application.GroupMemberService;
import com.kesi.tracker.group.domain.GroupMember;
import com.kesi.tracker.track.application.repository.TrackMemberRepository;
import com.kesi.tracker.track.domain.Track;
import com.kesi.tracker.track.domain.TrackMember;
import com.kesi.tracker.track.domain.event.TrackAppliedEvent;
import com.kesi.tracker.track.domain.event.TrackCanceledEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrackAssignmentServiceImpl implements TrackAssignmentService {
    private final TrackMemberRepository trackMemberRepository;
    private final TrackService trackService;
    private final GroupMemberService groupMemberService;
    private final ApplicationEventPublisher applicationEventPublisher;

    private TrackMember getTrackMemberByTrackIdAndUid(Long trackId, Long uid) {
        return trackMemberRepository.findByTrackIdAndUid(trackId, uid)
                .orElseThrow(() -> new RuntimeException("trackMember not found"));
    }

    @Override
    @Transactional
    public void applyTrack(Long currentUid, Long trackId) {
        Track track = trackService.getById(trackId);
        if(!track.isAssignmentWithinPeriod(LocalDateTime.now()))
            throw new IllegalArgumentException("수강 신청 기간만 신청 가능합니다");

        GroupMember groupMember = groupMemberService.getApprovedByGidAndUid(currentUid, track.getGid());

        if(!groupMember.isFollower())
            throw  new RuntimeException("follower만 신청이 가능합니다");

        trackMemberRepository.save(TrackMember.builder()
                        .uid(currentUid)
                        .trackId(trackId)
                        .createdAt(LocalDateTime.now())
                .build());

        List<GroupMember> leaderGroupMembers = groupMemberService.findByGidAndRoleIsLeader(groupMember.getGid());

        applicationEventPublisher.publishEvent(TrackAppliedEvent.builder()
                .appliedUserId(currentUid)
                .trackId(trackId)
                .groupId(groupMember.getGid())
                .hostId(track.getHostId())
                .groupLeaderIds(leaderGroupMembers.stream().map(GroupMember::getUid).collect(Collectors.toList()))
                .build());

    }

    @Override
    @Transactional
    public void cancelTrack(Long currentUid, Long trackId) {
        TrackMember trackMember = getTrackMemberByTrackIdAndUid(currentUid, trackId);
        Track track = trackService.getById(trackId);

        trackMemberRepository.deleteById(trackMember.getId());
        List<GroupMember> leaderGroupMembers = groupMemberService.findByGidAndRoleIsLeader(track.getGid());

        applicationEventPublisher.publishEvent(TrackCanceledEvent.builder()
                .canceledUserId(currentUid)
                .trackId(trackId)
                .groupId(track.getGid())
                .hostId(track.getHostId())
                .groupLeaderIds(leaderGroupMembers.stream().map(GroupMember::getUid).collect(Collectors.toList()))
                .build());

    }
}
