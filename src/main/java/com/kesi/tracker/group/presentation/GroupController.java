package com.kesi.tracker.group.presentation;

import com.kesi.tracker.group.application.GroupService;
import com.kesi.tracker.group.domain.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @GetMapping
    public Group getByGid(
            @PathVariable long gid
    ) {

    }

}
