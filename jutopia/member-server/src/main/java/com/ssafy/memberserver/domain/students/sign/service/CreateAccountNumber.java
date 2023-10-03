package com.ssafy.memberserver.domain.students.sign.service;

import java.util.Random;

public class CreateAccountNumber {
    private static final String ACCOUNT_NUMBER_FORMAT = "%03d-%05d-%04d";
    private static final int MAX_PART1 = 1000; // 3-digit number
    private static final int MAX_PART2 = 100000; // 5-digit number
    private static final int MAX_PART3 = 10000; // 4-digit number

    public String generateAccountNumber() {
        Random random = new Random();
        int part1 = random.nextInt(MAX_PART1);
        int part2 = random.nextInt(MAX_PART2);
        int part3 = random.nextInt(MAX_PART3);
        return String.format(ACCOUNT_NUMBER_FORMAT, part1, part2, part3);
    }
}
