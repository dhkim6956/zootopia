package com.ssafy.memberserver.domain.notice.dto.response;

import com.ssafy.memberserver.common.enums.NoticeStatus;
import com.ssafy.memberserver.domain.notice.entity.Notice;

public record NoticeResponse(
        Long id,
        String title,
        String content,
        Long viewCount,
        NoticeStatus noticeStatus
) {
    public static NoticeResponse from(Notice notice){
        return new NoticeResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getViewCount(),
                notice.getNoticeStatus()
        );
    }
}
