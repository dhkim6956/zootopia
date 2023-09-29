package com.ssafy.memberserver.domain.pointtransaction.entity;

import com.ssafy.memberserver.common.enums.PointType;
import com.ssafy.memberserver.domain.pointtransaction.dto.request.PointDepositRequest;
import com.ssafy.memberserver.domain.pointtransaction.dto.request.PointUpdateRequest;
import com.ssafy.memberserver.domain.pointtransaction.dto.request.PointWithDrawRequest;
import com.ssafy.memberserver.domain.students.entity.Student;
import jakarta.persistence.*;
import lombok.*;

import java.awt.*;
import java.math.BigDecimal;
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Entity
@Getter
public class PointTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String place;
    private BigDecimal deposit;
    private BigDecimal withDraw;
    private PointType pointType;
    @ManyToOne
    private Student student;

    public static PointTransaction from(PointDepositRequest pointDepositRequest, Student student){
        return PointTransaction.builder()
                .deposit(pointDepositRequest.deposit())
                .place(pointDepositRequest.place())
                .student(student)
                .build();
    }
    public static PointTransaction withDrawfrom(PointWithDrawRequest pointWithDrawRequest,Student student){
        return PointTransaction.builder()
                .withDraw(pointWithDrawRequest.withDraw())
                .place(pointWithDrawRequest.place())
                .student(student)
                .build();
    }

    //TODO: 마이너스 = 총값 - 마이너스 값인 경우 예외처리 필요
    public BigDecimal update(PointUpdateRequest pointUpdateRequest){
        BigDecimal updatedPoint = student.getPoint();
        if(student.getPoint() != null || pointUpdateRequest.deposit() != null || pointUpdateRequest.pointType() == PointType.DEPOSIT){
            BigDecimal currentPoint = student.getPoint();
            updatedPoint = currentPoint.add(pointUpdateRequest.deposit());
        } else if (student.getPoint() != null || pointUpdateRequest.output() != null || pointUpdateRequest.pointType() == PointType.DEPOSIT) {
            BigDecimal currentPoint = student.getPoint();
            updatedPoint = currentPoint.subtract(pointUpdateRequest.output());
        }
        return updatedPoint;
    }
}
