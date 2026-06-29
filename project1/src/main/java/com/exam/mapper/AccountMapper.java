package com.exam.mapper;

import com.exam.dto.AccountDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AccountMapper {
    List<AccountDTO> findAccount(int userId);

    // 계좌 1개 조회
    AccountDTO findAccountById(int accountId);

    // 출금 계좌 잔액 차감
    int withdraw(int accountId, int amount);

    // 입금 계좌 잔액 증가
    int deposit(int accountId, int amount);

    // 계좌 생성
    int insertAccount(AccountDTO accountDTO);
}
