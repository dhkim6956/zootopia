package com.ssafy.memberserver.domain.pointtransaction.service;

import com.ssafy.memberserver.common.enums.PointType;
import com.ssafy.memberserver.domain.pointtransaction.dto.request.PointDepositRequest;
import com.ssafy.memberserver.domain.pointtransaction.dto.request.PointUpdateRequest;
import com.ssafy.memberserver.domain.pointtransaction.dto.request.PointWithDrawRequest;
import com.ssafy.memberserver.domain.pointtransaction.dto.response.PointDepositResponse;
import com.ssafy.memberserver.domain.pointtransaction.dto.response.PointUpdateResponse;
import com.ssafy.memberserver.domain.pointtransaction.dto.response.PointWithDrawResponse;
import com.ssafy.memberserver.domain.pointtransaction.entity.PointTransaction;
import com.ssafy.memberserver.domain.pointtransaction.repository.PointTransactionRepository;
import com.ssafy.memberserver.domain.students.entity.Student;
import com.ssafy.memberserver.domain.students.repository.StudentRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class PointTransactionService {
    private final PointTransactionRepository pointTransactionRepository;
    private final StudentRepository studentRepository;

    @Operation(summary = "학생의 포인트 조회")
    @Transactional
    public List<PointTransaction> getPointAll(UUID studentId){
        return pointTransactionRepository.findByStudentId(studentId);
    }
    @Operation(summary = "조회")
    @Transactional
    public List<PointTransaction> getAll(){
        return pointTransactionRepository.findAll();
    }

    @Transactional
    public PointDepositResponse pointDeposit(PointDepositRequest pointDepositRequest,String studentId,BigDecimal deposit){
       Student student = studentRepository.findByStudentId(studentId)
               .orElseThrow(()-> new NoSuchElementException("학생 없소"));
       pointTransactionRepository.flush();
       BigDecimal updatedPoint = null;
       if(student.getPoint() != null || pointDepositRequest.deposit() != null){
           BigDecimal currentPoint = student.getPoint();
           updatedPoint = currentPoint.add(deposit);
           student.addPointUpdate(pointDepositRequest,updatedPoint);
           PointTransaction pt = pointTransactionRepository.save(PointTransaction.from(pointDepositRequest,student));
           pointTransactionRepository.flush();
           return PointDepositResponse.from(pt);

       }
       return null;
    }
    @Transactional
    public PointWithDrawResponse pointWithDraw(PointWithDrawRequest pointWithDrawRequest,String studentId, BigDecimal withDraw){
        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new NoSuchElementException("학생 없소"));
        BigDecimal updatedPoint = null;
        if (student.getPoint() != null || pointWithDrawRequest.withDraw() != null) {
            BigDecimal currentPoint = student.getPoint();
            updatedPoint = currentPoint.subtract(withDraw);
            student.subtractPointUpdate(pointWithDrawRequest,updatedPoint);
            PointTransaction pointTransaction = pointTransactionRepository.save(PointTransaction.withDrawfrom(pointWithDrawRequest,student));
            pointTransactionRepository.flush();
            return PointWithDrawResponse.from(pointTransaction);

        }
        return null;
    }

    @Transactional
    public PointUpdateResponse pointUpdate(PointUpdateRequest pointUpdateRequest,String studentId, Long id) {
        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new NoSuchElementException("학생을 찾을 수 없습니다."));

        // 포인트 거래를 찾고 존재하지 않으면 예외를 던집니다.
        PointTransaction pointTransaction = pointTransactionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("포인트 거래를 찾을 수 없습니다."));

        // 포인트 거래를 업데이트합니다.
        pointTransaction.update(pointUpdateRequest);

        // 업데이트 성공을 나타내는 응답을 반환합니다.
        return PointUpdateResponse.of(true);
    }
}
