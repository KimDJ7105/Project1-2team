package com.exam.mapper;

import com.exam.dto.AccountDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AccountMapper {
    List<AccountDTO> findAccount(int userId);

    // 계좌 1개 조회
    AccountDTO findAccountById(int accountId);

    // 계좌 번호로 계좌 조회
    AccountDTO findAccountByAccountNumber(String accountNumber);

    // 출금 계좌 잔액 차감
    int withdraw(Map<String, Object> map);

    // 입금 계좌 잔액 증가
    int deposit(Map<String, Object> map);

    // 계좌 생성
    int insertAccount(AccountDTO accountDTO);
}
