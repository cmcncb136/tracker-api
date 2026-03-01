package com.kesi.tracker.group.presentation;

import com.kesi.tracker.core.security.annotation.UserId;
import com.kesi.tracker.group.application.GroupService;
import com.kesi.tracker.group.application.dto.*;
import com.kesi.tracker.group.domain.GroupMemberStatus;
import com.kesi.tracker.user.application.UserService;
import com.kesi.tracker.user.application.dto.GroupMemberProfileResponse;
import com.kesi.tracker.user.application.dto.UserProfileResponse;
import com.kesi.tracker.user.domain.Email;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Group API", description = "그룹 라이프사이클 및 멤버십 관리")
@RestController
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    private final UserService userService;

    @Operation(summary = "그룹 상세 조회", description = "GID를 통해 특정 그룹의 상세 정보를 조회합니다.")
    @GetMapping("groups/{gid}")
    public GroupResponse getByGid(
            @PathVariable long gid,
            @UserId Long userId
    ) {
        return groupService.getGroupResponseByGid(gid, userId);
    }

    @Operation(summary = "공개 그룹 검색", description = "이름을 기반으로 공개된 그룹을 검색합니다.")
    @GetMapping("groups")
    public Page<GroupProfileResponse> search(
            @ModelAttribute GroupSearchRequest searchRequest,
            @PageableDefault Pageable pageable) {
        return groupService.searchPublicGroups(searchRequest, pageable);
    }

    @Operation(summary = "그룹 생성")
    @PostMapping("groups")
    public GroupResponse create(
            @RequestBody GroupCreationRequest groupCreationRequest,
            @UserId Long userId
    ) {
        return groupService.create(groupCreationRequest, userId);
    }

    @Operation(summary = "내 그룹 목록 조회", description = "로그인한 사용자가 속한 그룹 목록을 조회합니다.")
    @GetMapping("users/me/groups")
    public List<GroupProfileResponse> findByUserId(@UserId Long userId) {
        return groupService.getGroupResponseByUid(userId);
    }

    @Operation(summary = "내 그룹 멤버 조회", description = "그룹 안에 특정 상태에 멤버 목록을 조회합니다.")
    @GetMapping("/groups/{gid}/users")
    public List<GroupMemberProfileResponse> getUserProfileResponseByGid(
            @PathVariable Long gid,
            @RequestParam(defaultValue = "APPROVED") GroupMemberStatus status,
            @UserId Long userId
    ) {
        return groupService.getGroupMemberProfileResponseByGidAndGroupMemberStatus(gid, status, userId);
    }

    @Operation(summary = "내 그룹에서 자기 정보 조회", description = "그룹에서 내 정보(역할 등) 조회합니다.")
    @GetMapping("/groups/{gid}/me")
    public MyGroupInfoResponse getMyProfileResponseByGid(
            @PathVariable Long gid,
            @UserId Long userId
    ) {
        return groupService.getMyGroupInfoResponse(gid, userId);
    }


    @Operation(summary = "그룹 초대", description = "이메일을 통해 특정 사용자에게 그룹 초대장을 보냅니다.")
    @PostMapping("/groups/{gid}/invitations")
    public void invite(
            @PathVariable Long gid,
            @RequestParam String targetUserEmail,
            @UserId Long userId
            ) {
        groupService.invite(
                gid,
                new Email(targetUserEmail),
                userId
        );
    }

    @Operation(summary = "그룹 초대 수락")
    @PostMapping("/groups/{gid}/invitations/accept")
    public void acceptInvitation(
            @PathVariable Long gid,
            @UserId Long userId
    ) {
        groupService.acceptInvitation(gid, userId);
    }

    @Operation(summary = "그룹 가입 신청", description = "공개 그룹에 가입 신청을 보냅니다.")
    @PostMapping("groups/{gid}/join-requests")
    public void joinRequest(
            @PathVariable Long gid,
            @UserId Long userId
            ) {
        groupService.joinRequest(gid, userId);
    }

    @Operation(summary = "그룹 가입 신청 승인", description = "그룹 리더가 가입 신청을 승인합니다.")
    @PostMapping("groups/{gid}/join-requests/{requestId}/approve")
    public void approveJoinRequest(
            @PathVariable Long gid,
            @PathVariable Long requestId,
            @UserId Long userId
    ) {
        groupService.approveJoinRequest(
                gid, requestId, userId
        );
    }

    @Operation(summary = "트랙 팔로워 지정", description = "그룹원이 트랙의 팔로워(학습자) 역할을 수행하도록 변경합니다.")
    @PatchMapping("groups/{gid}/followers")
    public void registerFollower(
            @PathVariable Long gid,
            @RequestParam String targetUserEmail,
            @UserId Long userId
    ) {
        groupService.registerFollower(gid, userId, new Email(targetUserEmail));
    }

    @Operation(summary = "트랙 호스트 지정", description = "그룹원이 트랙의 호스트(교수자/리더) 역할을 수행하도록 변경합니다.")
    @PatchMapping("groups/{gid}/hosts")
    public void registerHost(
            @PathVariable Long gid,
            @RequestParam String targetUserEmail,
            @UserId Long userId
            ) {
        groupService.registerHost(gid, userId, new Email(targetUserEmail));
    }
}