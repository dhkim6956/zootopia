package com.ssafy.memberserver.domain.notice.repository;

import com.ssafy.memberserver.domain.notice.entity.Notice;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NoticeRepository extends JpaRepository<Notice,Long> {
    @Modifying
    @Query("UPDATE Notice n SET n.viewCount = n.viewCount + :viewCount + 1 WHERE n.id = :id")
    int updateByViewCount(@Param("viewCount") Long viewCount, @Param("id") Long id);
}
