package com.ssafy.memberserver.domain.notice.repository;

import com.ssafy.memberserver.domain.notice.entity.Notice;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NoticeRepository extends JpaRepository<Notice,Long> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE notice SET view_count = view_count + 1 WHERE id = :id", nativeQuery = true)
    int updateViewCount(@Param("id") Long id);
}
