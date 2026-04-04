package com.kesi.tracker.track;

import com.kesi.tracker.core.exception.BusinessException;
import com.kesi.tracker.core.exception.ErrorCode;
import com.kesi.tracker.track.application.TrackMemberService;
import com.kesi.tracker.track.application.repository.TrackMemberRepository;
import com.kesi.tracker.track.domain.TrackMember;
import com.kesi.tracker.track.domain.TrackRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrackMemberServiceImpl implements TrackMemberService {
    private final TrackMemberRepository trackMemberRepository;

    @Override
    public TrackMember createTrackHost(Long trackId, Long hostId) {
        TrackMember hostMember = TrackMember.builder()
                .trackId(trackId)
                .uid(hostId)
                .role(TrackRole.HOST)
                .build();

        return trackMemberRepository.save(hostMember);
    }

    @Override
    public TrackMember save(TrackMember trackMember) {
        return trackMemberRepository.save(trackMember);
    }

    @Override
    public TrackMember getTrackMemberByTrackIdAndUid(Long trackId, Long uid) {
        return trackMemberRepository.findByTrackIdAndUid(trackId, uid)
                .orElseThrow(() -> new BusinessException(ErrorCode.TRACK_MEMBER_NOT_FOUND));
    }

    @Override
    public void deleteById(Long id) {
        trackMemberRepository.deleteById(id);
    }
}
