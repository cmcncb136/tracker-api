package com.kesi.tracker.notice.application;

import com.kesi.tracker.core.exception.BusinessException;
import com.kesi.tracker.core.exception.ErrorCode;
import com.kesi.tracker.file.application.FileService;
import com.kesi.tracker.file.domain.FileOwner;
import com.kesi.tracker.group.application.GroupMemberService;
import com.kesi.tracker.notice.application.dto.NoticeCreationRequest;
import com.kesi.tracker.notice.application.dto.NoticeResponse;
import com.kesi.tracker.notice.application.dto.NoticeTitleResponse;
import com.kesi.tracker.notice.application.dto.NoticeUpdateRequest;
import com.kesi.tracker.notice.application.mapper.NoticeMapper;
import com.kesi.tracker.notice.application.repository.NoticeRepository;
import com.kesi.tracker.notice.domain.Notice;
import com.kesi.tracker.user.application.UserService;
import com.kesi.tracker.user.application.dto.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;
    private final FileService fileService;
    private final GroupMemberService groupMemberService;
    private final UserService userService;

    @Override
    public Notice create(Notice notice) {
        return noticeRepository.create(notice);
    }

    @Override
    public NoticeResponse create(NoticeCreationRequest request, Long gid, Long currentUid) {
        if(!groupMemberService.isGroupLeader(gid, currentUid))
            throw new IllegalArgumentException("공지는 리더만 작성할 수 있습니다");

        Notice notice = create(NoticeMapper.toNotice(request, gid, currentUid));

        FileOwner owner = FileOwner.ofNotice(notice.getId());
        fileService.assignFileOwner(owner, request.getAttachmentFileIds());

        return toNoticeResponse(notice);
    }

    @Override
    public Notice update(Notice notice) {
        return noticeRepository.update(notice);
    }

    @Override
    public NoticeResponse update(NoticeUpdateRequest request, Long currentUid) {
        Notice original = getById(request.getId());
        if(!groupMemberService.isGroupLeader(original.getGid(), currentUid))
            throw new IllegalArgumentException("공지는 리더만 수정할 수 있습니다");


        Notice notice = update(NoticeMapper.toNotice(request, original, currentUid));
        fileService.updateFromFileOwner(FileOwner.ofNotice(notice.getId()), request.getProfileFileIds());

        return toNoticeResponse(notice);
    }

    @Override
    public Notice getById(Long noticeId) {
        return noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOTICE_NOT_FOUND));
    }

    @Override
    public NoticeResponse getById(Long id, Long currentUid) {
        Notice notice = getById(id);
        if(!groupMemberService.isGroupMember(notice.getGid(), currentUid))
            throw new IllegalArgumentException("해당 공지 접근 권한이 없습니다");

        return toNoticeResponse(notice);
    }

    private NoticeResponse toNoticeResponse(Notice notice) {
        return NoticeMapper.toNoticeResponse(
                notice,
                userService.getProfile(notice.getAuthorId()),
                userService.getProfile(notice.getModifiedBy()),
                fileService.findAccessUrlByOwner(FileOwner.ofNotice(notice.getId()))
        );
    }
    @Override
    public Page<NoticeTitleResponse> search(Long gid, String keyword, Long currentUid, Pageable pageable) {
        if(!groupMemberService.isGroupMember(gid, currentUid))
            throw new IllegalArgumentException("같은 소속인 경우만 조회가 가능합니다");
        Page<Notice> notices = noticeRepository.findByGidAndTitleContainingIgnoreCase(gid, keyword, pageable);

        HashSet userIdSet = new HashSet<>();
        userIdSet.addAll(notices.map(Notice::getAuthorId).stream().toList());
        userIdSet.addAll(notices.map(Notice::getModifiedBy).stream().toList());

        Map<Long, UserProfileResponse> userProfileMap = userService.getProfiles(userIdSet);

        return notices.map(notice -> NoticeMapper.toNoticeTitleResponse(
                notice,
                userProfileMap.get(notice.getAuthorId()),
                userProfileMap.get(notice.getModifiedBy())
        ));
    }
}
