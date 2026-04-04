package com.kesi.tracker.track.application;

import com.kesi.tracker.core.exception.BusinessException;
import com.kesi.tracker.core.exception.ErrorCode;
import com.kesi.tracker.group.application.GroupMemberService;
import com.kesi.tracker.group.domain.GroupMember;
import com.kesi.tracker.track.application.repository.TrackMemberRepository;
import com.kesi.tracker.track.application.repository.TrackRepository;
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
    private final TrackMemberService trackMemberService;
    private final TrackService trackService;
    private final GroupMemberService groupMemberService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final TrackRepository trackRepository;

    @Override
    @Transactional
    public void applyTrack(Long currentUid, Long trackId) {
        Track track = trackService.getById(trackId);
        if(!track.isAssignmentWithinPeriod(LocalDateTime.now()))
            throw new BusinessException(ErrorCode.TRACK_REGISTRATION_CLOSED);

        GroupMember groupMember = groupMemberService.getApprovedByGidAndUid(currentUid, track.getGid());

        if(!groupMember.isFollower())
            throw new BusinessException(ErrorCode.NOT_FOLLOWER);

        if(!trackRepository.applyById(trackId)) {
            throw new BusinessException(ErrorCode.TRACK_FULL);
        }

        trackMemberService.save(TrackMember.createFollower(track.getId(), currentUid));

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
        TrackMember trackMember = trackMemberService.getTrackMemberByTrackIdAndUid(currentUid, trackId);

        Track track = trackService.getById(trackId);
        List<GroupMember> leaderGroupMembers = groupMemberService.findByGidAndRoleIsLeader(track.getGid());

        if(!trackRepository.cancelById(trackId, currentUid)) {
            throw new BusinessException(ErrorCode.CANNOT_CANCEL_TRACK);
        }
        //Todo. 동시성 문제를 고려해서 풀 수 있도록 추후 수정
        trackMemberService.deleteById(trackMember.getId());

        applicationEventPublisher.publishEvent(TrackCanceledEvent.builder()
                .canceledUserId(currentUid)
                .trackId(trackId)
                .groupId(track.getGid())
                .hostId(track.getHostId())
                .groupLeaderIds(leaderGroupMembers.stream().map(GroupMember::getUid).collect(Collectors.toList()))
                .build());
    }
}
