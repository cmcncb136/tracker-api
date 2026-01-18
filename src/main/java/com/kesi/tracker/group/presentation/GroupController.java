package com.kesi.tracker.group.presentation;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("groups")
public class GroupController {
    private final GroupService groupService;

    @GetMapping("/{gid}")
    public GroupResponse getByGid(
            @PathVariable long gid
    ) {
        //Todo. 추후 구현되면 UID 가져오기
        return groupService.getGroupResponseByGid(gid, null);
    }

    @GetMapping
    public Page<GroupProfileResponse> search(
            @ModelAttribute  GroupSearchRequest searchRequest,
            @PageableDefault Pageable pageable) {
        return groupService.searchPublicGroups(searchRequest, pageable);
    }

    @PostMapping
    public GroupResponse create(
            @RequestBody GroupCreationRequest groupCreationRequest
            ) {
        //Todo. UID 가져오기

        return null;
    }
}