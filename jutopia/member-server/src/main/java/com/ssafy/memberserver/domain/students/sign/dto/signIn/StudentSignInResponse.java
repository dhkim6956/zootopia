package com.ssafy.memberserver.domain.students.sign.dto.signIn;

import com.ssafy.memberserver.domain.students.entity.Student;
import com.ssafy.memberserver.domain.students.sign.dto.signUp.StudentSignUpResponse;
import lombok.Builder;

import java.util.UUID;

@Builder
public record StudentSignInResponse(
        UUID id
) {
    public static StudentSignInResponse from(Student student){
        return StudentSignInResponse.builder()
                .id(student.getId())
                .build();
    }
}
