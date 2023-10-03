package com.ssafy.memberserver.domain.notice.dto.response;

import com.ssafy.memberserver.common.enums.NoticeStatus;
import com.ssafy.memberserver.domain.notice.entity.Notice;
import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record NoticeResponse(
        Long id,
        String title,
        String content,
        Long viewCount,
        LocalDateTime createdAt,
        NoticeStatus noticeStatus
) {
    public static NoticeResponse from(Notice notice){
        return NoticeResponse.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .viewCount(notice.getViewCount())
                .createdAt(notice.getCreatedAt())
                .noticeStatus(notice.getNoticeStatus())
                .build();
    }
}