package com.kesi.tracker.group.presentation;

import com.kesi.tracker.core.security.annotation.UserId;
import com.kesi.tracker.group.application.GroupService;
import com.kesi.tracker.group.application.dto.*;
import com.kesi.tracker.user.domain.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @GetMapping("groups/{gid}")
    public GroupResponse getByGid(
            @PathVariable long gid,
            @UserId Long userId
    ) {
        return groupService.getGroupResponseByGid(gid, userId);
    }

    @GetMapping("groups")
    public Page<GroupProfileResponse> search(
            @ModelAttribute GroupSearchRequest searchRequest,
            @PageableDefault Pageable pageable) {
        return groupService.searchPublicGroups(searchRequest, pageable);
    }

    @PostMapping("groups")
    public GroupResponse create(
            @RequestBody GroupCreationRequest groupCreationRequest,
            @UserId Long userId
    ) {
        return groupService.create(groupCreationRequest, userId);
    }

    @GetMapping("users/me/groups")
    public List<GroupProfileResponse> findByUserId(@UserId Long userId) {
        return groupService.getGroupResponseByUid(userId);
    }

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

    @PostMapping("groups/{gid}/join-requests")
    public void joinRequest(
            @PathVariable Long gid,
            @UserId Long userId
            ) {
        groupService.joinRequest(gid, userId);
    }

    @PatchMapping("groups/{gid}/followers")
    public void registerFollower(
            @PathVariable Long gid,
            @RequestParam String targetUserEmail,
            @UserId Long userId
    ) {
        groupService.registerFollower(gid, userId, new Email(targetUserEmail));
    }

    @PatchMapping("groups/{gid}/hosts")
    public void registerHost(
            @PathVariable Long gid,
            @RequestParam String targetUserEmail,
            @UserId Long userId
            ) {
        groupService.registerHost(gid, userId, new Email(targetUserEmail));
    }
}