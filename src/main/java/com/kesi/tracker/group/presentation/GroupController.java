package com.kesi.tracker.group.presentation;

import com.kesi.tracker.core.security.annotation.UserId;
import com.kesi.tracker.group.application.GroupService;
import com.kesi.tracker.group.application.dto.GroupCreationRequest;
import com.kesi.tracker.group.application.dto.GroupProfileResponse;
import com.kesi.tracker.group.application.dto.GroupResponse;
import com.kesi.tracker.group.application.dto.GroupSearchRequest;
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
            @ModelAttribute  GroupSearchRequest searchRequest,
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

    @GetMapping("users/groups")
    public List<GroupProfileResponse> findByUserId(@UserId Long userId) {
        return groupService.getGroupResponseByUid(userId);
    }
}