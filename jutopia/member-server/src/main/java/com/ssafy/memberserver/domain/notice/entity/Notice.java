package com.ssafy.memberserver.domain.notice.entity;

import com.ssafy.memberserver.common.enums.NoticeStatus;
import com.ssafy.memberserver.domain.notice.dto.request.NoticeDeleteRequest;
import com.ssafy.memberserver.domain.notice.dto.request.NoticeRequest;
import com.ssafy.memberserver.domain.notice.dto.request.NoticeUpdateRequest;
import com.ssafy.memberserver.domain.teachers.entity.Teacher;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private Long viewCount;
    @Enumerated(EnumType.STRING)
    private NoticeStatus noticeStatus;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "teacher_id")
//    private Teacher teacher;

    public static Notice from(NoticeRequest noticeRequest){
        return Notice.builder()
                .id(noticeRequest.id())
                .title(noticeRequest.title())
                .content(noticeRequest.content())
                .createdAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .viewCount(0L)
                .noticeStatus(NoticeStatus.ACTIVE)
                .build();
    }

    public void update(NoticeUpdateRequest noticeUpdateRequest) {
        if (noticeUpdateRequest.newTitle() != null) {
            this.title = noticeUpdateRequest.newTitle();
        }
        if (noticeUpdateRequest.newContent() != null) {
            this.content = noticeUpdateRequest.newContent();
        }
        this.updateAt = LocalDateTime.now();
    }
    public void delete(NoticeDeleteRequest noticeDeleteRequest){
        if(noticeDeleteRequest.noticeStatus() == NoticeStatus.ACTIVE){
            this.noticeStatus = NoticeStatus.INACTIVE;
        }
    }
}
