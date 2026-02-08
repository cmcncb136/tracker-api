package com.kesi.tracker.notice.application.mapper;

import com.kesi.tracker.file.domain.FileAccessUrl;
import com.kesi.tracker.notice.application.dto.NoticeCreationRequest;
import com.kesi.tracker.notice.application.dto.NoticeResponse;
import com.kesi.tracker.notice.application.dto.NoticeTitleResponse;
import com.kesi.tracker.notice.application.dto.NoticeUpdateRequest;
import com.kesi.tracker.notice.domain.Notice;
import com.kesi.tracker.user.application.dto.UserProfileResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class NoticeMapper {
    public static Notice toNotice(NoticeCreationRequest request, Long gid, Long currentUid) {
        return Notice.builder()
                .gid(gid)
                .authorId(currentUid)
                .type(request.getType())
                .title(request.getTitle())
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .createdBy(currentUid)
                .modifiedBy(currentUid)
                .build();
    }

    public static Notice toNotice(NoticeUpdateRequest request, Notice original, Long currentUid) {
        return Notice.builder()
                .id(original.getId())
                .gid(original.getGid())
                .authorId(original.getAuthorId())
                .type(request.getType())
                .title(request.getTitle())
                .content(request.getContent())
                .createdAt(original.getCreatedAt())
                .modifiedAt(LocalDateTime.now())
                .createdBy(original.getCreatedBy())
                .modifiedBy(currentUid)
                .build();
    }

    public static NoticeResponse toNoticeResponse(
            Notice notice,
            UserProfileResponse authorProfile,
            UserProfileResponse modifiedAuthorProfile,
            List<FileAccessUrl> attachmentFileAccessUrls) {
        return NoticeResponse.builder()
                .id(notice.getId())
                .gid(notice.getGid())
                .type(notice.getType())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createdAt(notice.getCreatedAt())
                .modifiedAt(notice.getModifiedAt())
                .authorProfile(authorProfile)
                .modifiedAuthorProfile(modifiedAuthorProfile)
                .attachmentFileUrls(attachmentFileAccessUrls.stream().map(FileAccessUrl::toString).toList())
                .build();
    }

    public static NoticeTitleResponse toNoticeTitleResponse(
            Notice notice,
            UserProfileResponse authorProfile,
            UserProfileResponse modifiedAuthorProfile
    ) {
        return NoticeTitleResponse.builder()
                .id(notice.getId())
                .gid(notice.getGid())
                .type(notice.getType())
                .title(notice.getTitle())
                .authorProfile(authorProfile)
                .modifiedAuthorProfile(modifiedAuthorProfile)
                .createdAt(notice.getCreatedAt())
                .modifiedAt(notice.getModifiedAt())
                .build();
    }
}






