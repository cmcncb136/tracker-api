package com.kesi.tracker.track.application;

import com.kesi.tracker.group.application.GroupMemberService;
import com.kesi.tracker.group.domain.GroupMember;
import com.kesi.tracker.track.application.repository.TrackMemberRepository;
import com.kesi.tracker.track.domain.Track;
import com.kesi.tracker.track.domain.TrackMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TrackApplyServiceImpl implements TrackApplyService {
    private final TrackMemberRepository trackMemberRepository;
    private final TrackService trackService;
    private final GroupMemberService groupMemberService;

    private TrackMember getTrackMemberByTrackIdAndUid(Long trackId, Long uid) {
        return trackMemberRepository.findByTrackIdAndUid(trackId, uid)
                .orElseThrow(() -> new RuntimeException("trackMember not found"));
    }

    @Override
    public void applyTrack(Long currentUid, Long trackId) {
        Track track = trackService.getById(trackId);
        GroupMember groupMember = groupMemberService.getApprovedByGidAndUid(currentUid, track.getGid());

        if(!groupMember.isFollower())
            throw  new RuntimeException("follower만 신청이 가능합니다");

        trackMemberRepository.save(TrackMember.builder()
                        .uid(currentUid)
                        .trackId(trackId)
                        .createdAt(LocalDateTime.now())
                .build());
    }

    @Override
    public void cancelTrack(Long currentUid, Long trackId) {
        TrackMember trackMember = getTrackMemberByTrackIdAndUid(currentUid, trackId);
        trackMemberRepository.deleteById(trackMember.getId());
    }
}
