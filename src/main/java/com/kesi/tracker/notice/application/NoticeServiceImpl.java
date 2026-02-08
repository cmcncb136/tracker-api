package com.kesi.tracker.notice.application;

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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;
    private final FileService fileService;
    private final GroupMemberService groupMemberService;

    @Override
    public Notice create(Notice notice) {
        return noticeRepository.create(notice);
    }

    @Override
    public void create(NoticeCreationRequest request, Long currentUid) {
        if(!groupMemberService.isGroupLeader(request.getGid(), currentUid))
            throw new IllegalArgumentException("공지는 리더만 작성할 수 있습니다");

        Notice notice = create(NoticeMapper.toNotice(request, currentUid));

        FileOwner owner = FileOwner.ofNotice(notice.getId());
        fileService.assignFileOwner(owner, request.getAttachmentFileIds());
    }

    @Override
    public Notice update(Notice notice) {
        return noticeRepository.update(notice);
    }

    @Override
    public Notice update(NoticeUpdateRequest request, Long currentUid) {
        Notice original = getById(request.getId());
        if(!groupMemberService.isGroupLeader(original.getGid(), currentUid))
            throw new IllegalArgumentException("공지는 리더만 수정할 수 있습니다");


        Notice notice = update(NoticeMapper.toNotice(request, original, currentUid));
        fileService.updateFromFileOwner(FileOwner.ofNotice(notice.getId()), request.getProfileFileIds());

        return null;
    }

    @Override
    public Notice getById(Long noticeId) {
        return noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 공지를 찾을 수 없습니다."));
    }

    @Override
    public NoticeResponse getById(Long id, Long currentUid) {
        return null;
    }

    @Override
    public Page<NoticeTitleResponse> search(Long gid, String keyword, Pageable pageable) {
        return null;
    }
}
