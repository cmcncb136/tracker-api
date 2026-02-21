package com.kesi.tracker.track.application;

import com.kesi.tracker.core.exception.BusinessException;
import com.kesi.tracker.core.exception.ErrorCode;
import com.kesi.tracker.file.application.FileService;
import com.kesi.tracker.file.domain.FileAccessUrl;
import com.kesi.tracker.file.domain.FileOwner;
import com.kesi.tracker.group.application.GroupMemberService;
import com.kesi.tracker.group.application.GroupService;
import com.kesi.tracker.group.application.dto.GroupProfileResponse;
import com.kesi.tracker.file.domain.FileOwners;
import com.kesi.tracker.group.domain.GroupMember;
import com.kesi.tracker.track.application.dto.*;
import com.kesi.tracker.track.application.mapper.TrackMapper;
import com.kesi.tracker.track.application.query.TrackSearchCondition;
import com.kesi.tracker.track.application.query.TrackWithGroupSearchCondition;
import com.kesi.tracker.track.application.repository.TrackRepository;
import com.kesi.tracker.track.domain.Track;
import com.kesi.tracker.track.domain.event.TrackCreatedEvent;
import com.kesi.tracker.user.application.UserService;
import com.kesi.tracker.user.application.dto.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackServiceImpl implements TrackService {
    private final TrackRepository trackRepository;

    private final GroupMemberService groupMemberService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserService userService;
    private final FileService fileService;
    private final GroupService groupService;

    @Override
    public Track create(Track track, Long currentUid) {
        //TRACK에 있는 그룹이 존재하고 해당 TRACK에 승인된 멤버이어야 한다
        GroupMember groupMember = groupMemberService.getApprovedByGidAndUid(track.getGid(), currentUid);


        //HOST 역할을 붙어야 받아야 한다
        if (!groupMember.isHost())
            throw new BusinessException(ErrorCode.NOT_GROUP_HOST);


        Track createdTrack = trackRepository.save(track);


        List<GroupMember> leaderGroupMembers = groupMemberService.findByGidAndRoleIsLeader(groupMember.getGid());
        applicationEventPublisher.publishEvent(TrackCreatedEvent.builder()
                .trackId(createdTrack.getId())
                .groupId(groupMember.getGid())
                .createdByUserId(groupMember.getUid())
                .groupLeaderIds(leaderGroupMembers.stream().map(GroupMember::getUid).collect(Collectors.toList()))
                .build());


        return createdTrack;
    }

    @Override
    public Track update(Track track, Long currentUid) {
        //TRACK에 있는 그룹이 존재하고 해당 TRACK에 승인된 멤버이어야 한다
        GroupMember groupMember = groupMemberService.getApprovedByGidAndUid(track.getGid(), currentUid);

        //HOST 역할을 붙어야 받아야 한다
        if (!groupMember.isHost())
            throw new BusinessException(ErrorCode.NOT_GROUP_HOST);

        return trackRepository.save(track);
    }

    @Override
    public Track getById(Long id) {
        return trackRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.TRACK_NOT_FOUND));
    }


    @Override
    public List<Track> findByGid(Long gid) {
        return trackRepository.findByGid(gid);
    }

    @Override
    public TrackResponse getTrackResponseById(Long id, Long currentUid) {
        Track track = getById(id);

        //그룹 멤버인지 확인
        if (!groupMemberService.isGroupMember(track.getGid(), currentUid    ))
            throw new BusinessException(ErrorCode.NOT_GROUP_MEMBER);

        UserProfileResponse hostProfile = userService.getProfile(track.getHostId());

        return TrackMapper.toTrackResponse(
                track,
                hostProfile,
                fileService.findAccessUrlByOwner(FileOwner.ofTrack(track.getId()))
        );
    }

    @Override
    public Page<TrackResponse> searchTrackInGroup(Long gid, Long currentUid, TrackSearchRequest searchRequest, Pageable pageable) {
        if (!groupMemberService.isGroupMember(gid, currentUid))
            throw new BusinessException(ErrorCode.NOT_GROUP_MEMBER);

        TrackSearchCondition searchCondition = searchRequest.toTrackSearchCondition();
        Page<Track> tracks = trackRepository.searchInGroup(gid, searchCondition, pageable);

        Map<Long, UserProfileResponse> userProfileResponseMap
                = userService.getProfiles(tracks.map(Track::getHostId).toSet());

        Map<Long, List<FileAccessUrl>> fileAccessUrlsMap
                = fileService.findAccessUrlByOwners(FileOwners.ofTrack(tracks.map(Track::getId).toList()));

        return tracks.map(track -> TrackMapper.toTrackResponse(
                track,
                userProfileResponseMap.get(track.getHostId()),
                fileAccessUrlsMap.get(track.getId())
        ));
    }

    @Override
    public Page<TrackWithGroupResponse> searchTrackInGroupInUser(Long currentUid, TrackWithGroupSearchRequest searchRequest, Pageable pageable) {
        TrackWithGroupSearchCondition searchCondition = searchRequest.toTrackWithGroupSearchCondition();
        Page<Track> tracks = trackRepository.searchInGroupInUser(currentUid, searchCondition, pageable);

        Map<Long, UserProfileResponse> userProfileResponseMap
                = userService.getProfiles(tracks.map(Track::getHostId).toSet());

        Map<Long, GroupProfileResponse> groupProfileResponseMap
                = groupService.getGroupResponseByGids(tracks.map(Track::getHostId).toSet());

        Map<Long, List<FileAccessUrl>> fileAccessUrlsMap
                = fileService.findAccessUrlByOwners(FileOwners.ofTrack(tracks.map(Track::getId).toList()));

        return tracks.map(track -> TrackMapper.toTrackWithGroupResponse(
                TrackMapper.toTrackResponse(
                        track,
                        userProfileResponseMap.get(track.getHostId()),
                        fileAccessUrlsMap.get(track.getId())
                ),
                groupProfileResponseMap.get(track.getGid())
        ));
    }

    @Override
    public TrackResponse create(TrackCreationRequest trackCreationRequest, Long currentUid) {
        Track track = TrackMapper.toTrack(trackCreationRequest, currentUid);
        Track savedTrack =  this.create(track, currentUid);

        UserProfileResponse hostProfile = userService.getProfile(track.getHostId());

        return TrackMapper.toTrackResponse(
                savedTrack,
                hostProfile,
                fileService.findAccessUrlByOwner(FileOwner.ofTrack(track.getId()))
        );
    }
}



