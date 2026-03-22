package com.kesi.tracker.user.application.dto;

import com.kesi.tracker.file.domain.FileAccessUrl;
import com.kesi.tracker.user.domain.User;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public record UserComposite(
        User user,
        List<FileAccessUrl> accessUrls
) {
    public static List<UserComposite> from(
            List<User> users,
            Map<Long, List<FileAccessUrl>> userComposite
    ) {
        return users.stream()
                .map(user -> new UserComposite(
                        user,
                        userComposite.getOrDefault(user.getId(), Collections.emptyList())
                ))
                .toList();
    }

    public Long getUid() {
        return user.getId();
    }
}
