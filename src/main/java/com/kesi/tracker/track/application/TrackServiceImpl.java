package com.kesi.tracker.track.application;

import com.kesi.tracker.group.application.GroupMemberService;
import com.kesi.tracker.group.application.GroupService;
import com.kesi.tracker.group.domain.Group;
import com.kesi.tracker.group.domain.GroupMember;
import com.kesi.tracker.track.application.repository.TrackRepository;
import com.kesi.tracker.track.domain.Track;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrackServiceImpl implements TrackService {
    private final TrackRepository trackRepository;

    private final GroupMemberService groupMemberService;

    @Override
    public Track createTrack(Track track, Long currentUid) {
        //TRACK에 있는 그룹이 존재하고 해당 TRACK에 승인된 멤버이어야 한다
        GroupMember groupMember = groupMemberService.getApprovedByGidAndUid(track.getGid(), currentUid);

        //HOST 역할을 붙어야 받아야 한다
        if(!groupMember.isHost())
            throw new RuntimeException("Group member is not host");

        return trackRepository.save(track);
    }

    @Override
    public Track updateTrack(Track track, Long currentUid) {
        //TRACK에 있는 그룹이 존재하고 해당 TRACK에 승인된 멤버이어야 한다
        GroupMember groupMember = groupMemberService.getApprovedByGidAndUid(track.getGid(), currentUid);

        //HOST 역할을 붙어야 받아야 한다
        if(!groupMember.isHost())
            throw new RuntimeException("Group member is not host");

        return trackRepository.save(track);
    }

    @Override
    public Track getById(Long id) {
        return  trackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Track not found"));
    }


    @Override
    public List<Track> findByGid(Long gid) {
        return trackRepository.findByGid(gid);
    }
}
