package com.ssafy.memberserver.domain.pointtransaction.service;

import com.ssafy.memberserver.domain.pointtransaction.dto.request.PointExpenseRequest;
import com.ssafy.memberserver.domain.pointtransaction.dto.request.PointIncomeRequest;
import com.ssafy.memberserver.domain.pointtransaction.dto.response.PointExpenseResponse;
import com.ssafy.memberserver.domain.pointtransaction.dto.response.PointIncomeResponse;
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
import java.util.NoSuchElementException;


@Slf4j
@RequiredArgsConstructor
@Service
public class PointTransactionService {
    private final PointTransactionRepository pointTransactionRepository;
    private final StudentRepository studentRepository;

    @Operation(summary = "특정 학생의 포인트 조회")
    @Transactional(readOnly = true)
    public BigDecimal getStudentPoint(String studentId) {
        return studentRepository.findByStudentId(studentId)
                .map(Student::getPoint)
                .orElseThrow(() -> new NoSuchElementException("학생의 포인트를 찾을 수 없습니다."));
    }
    @Transactional
    public PointIncomeResponse pointIncome(PointIncomeRequest pointIncomeRequest, String studentId, BigDecimal income){
        return studentRepository.findByStudentId(studentId)
                .map(student -> {
                    if (student.getPoint() != null && pointIncomeRequest.income() != null) {
                        BigDecimal pointIncome = student.getPoint().add(income);
                        student.pointIncomeUpdate(pointIncomeRequest, pointIncome);
                        PointTransaction point = pointTransactionRepository.save(PointTransaction.incomeFrom(pointIncomeRequest, student));
                        pointTransactionRepository.flush();
                        return PointIncomeResponse.from(point);
                    } else {
                        throw new IllegalArgumentException("유효하지 않은 포인트나 수입입니다.");
                    }
                })
                .orElseThrow(() -> new NoSuchElementException("학생이 존재하지 않습니다."));
    }
    @Transactional
    public PointExpenseResponse pointExpense(PointExpenseRequest pointExpenseRequest, String studentId, BigDecimal expense){
        return studentRepository.findByStudentId(studentId)
                .map(student -> {
                    if (student.getPoint() != null && pointExpenseRequest.expense() != null) {
                        BigDecimal pointExpense = student.getPoint().subtract(expense);
                        student.pointExpenseUpdate(pointExpenseRequest, pointExpense);
                        PointTransaction point = pointTransactionRepository.save(PointTransaction.expenseFrom(pointExpenseRequest, student));
                        pointTransactionRepository.flush();
                        return PointExpenseResponse.from(point);
                    } else {
                        throw new IllegalArgumentException("유효하지 않은 포인트나 지출입니다.");
                    }
                })
                .orElseThrow(() -> new NoSuchElementException("학생이 존재하지 않습니다."));
        }
}
