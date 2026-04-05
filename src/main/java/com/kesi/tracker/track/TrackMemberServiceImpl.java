package com.kesi.tracker.track;

import com.kesi.tracker.core.exception.BusinessException;
import com.kesi.tracker.core.exception.ErrorCode;
import com.kesi.tracker.track.application.TrackMemberService;
import com.kesi.tracker.track.application.repository.TrackMemberRepository;
import com.kesi.tracker.track.domain.TrackMember;
import com.kesi.tracker.track.domain.TrackRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackMemberServiceImpl implements TrackMemberService {
    private final TrackMemberRepository trackMemberRepository;

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
    public List<TrackMember> findByTrackId(Long trackId) {
        return trackMemberRepository.findByTrackId(trackId);
    }

    @Override
    public List<TrackMember> findByTrackIdAndRole(Long trackId, TrackRole role) {
        return trackMemberRepository.findByTrackIdAndRole(trackId, role);
    }

    @Override
    public void deleteById(Long id) {
        trackMemberRepository.deleteById(id);
    }
}
