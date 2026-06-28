package com.exam.service;

import com.exam.dto.AccountDTO;

import java.util.List;

public interface AccountService {
    List<AccountDTO> findAccount(int userId);

    // 계좌 1개 조회
    AccountDTO findAccountById(int accountId);

    // 출금 계좌 잔액 차감
    int withdraw(int accountId, int amount);

    // 입금 계좌 잔액 증가
    int deposit(int accountId, int amount);

    // 계좌 생성
}
