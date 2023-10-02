package com.ssafy.memberserver.domain.students.repository;

import com.ssafy.memberserver.common.enums.MemberRole;
import com.ssafy.memberserver.domain.pointtransaction.entity.PointTransaction;
import com.ssafy.memberserver.domain.students.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    Optional<Student> findByStudentId(String studentId);
    boolean existsByStudentId(String studentId);
    List<Student> findBySchoolAndGradeAndClassRoom(String school,int grade,int classRoom);
    Optional<Student> findByStudentIdAndMemberRole(String studentId, MemberRole memberRole);
}
