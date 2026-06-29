package com.exam.service;

import com.exam.dto.AccountDTO;
import com.exam.dto.TradingHistoryDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {

    AccountService accountService;
    TradingHistoryService tradingHistoryService;

    public TransactionServiceImpl(AccountService accountService,
                                  TradingHistoryService tradingHistoryService) {
        this.accountService = accountService;
        this.tradingHistoryService = tradingHistoryService;
    }

    @Override
    @Transactional
    public int sendMoneyProcess(TradingHistoryDTO tradingHistoryDTO) {

        // 거래 시간 저장 (FDS에서 사용할 예정)
        tradingHistoryDTO.setTradeDate(LocalDateTime.now());

        // 출금 계좌 조회
        AccountDTO sendingAccount =
                accountService.findAccountById(tradingHistoryDTO.getSendingAccount());

        // 계좌번호로 입금 계좌 조회
        AccountDTO receivingAccount =
                accountService.findAccountByAccountNumber(
                        tradingHistoryDTO.getReceivingAccountNumber());

        // 출금 계좌 없는 경우
        if (sendingAccount == null) {
            tradingHistoryDTO.setIsFail(true);
            tradingHistoryDTO.setFailMsg("출금 계좌가 존재하지 않습니다.");
            tradingHistoryService.insertTradingHistory(tradingHistoryDTO);
            return 0;
        }

        // 입금 계좌 없는 경우
        if (receivingAccount == null) {
            tradingHistoryDTO.setIsFail(true);
            tradingHistoryDTO.setFailMsg("입금 계좌가 존재하지 않습니다.");
            tradingHistoryService.insertTradingHistory(tradingHistoryDTO);
            return 0;
        }

        // 조회한 accountId 저장
        tradingHistoryDTO.setReceivingAccount(receivingAccount.getAccountId());

        // 잔액 부족 확인
        if (sendingAccount.getBalance() < tradingHistoryDTO.getAmount()) {
            tradingHistoryDTO.setIsFail(true);
            tradingHistoryDTO.setFailMsg("잔액이 부족합니다.");
            tradingHistoryService.insertTradingHistory(tradingHistoryDTO);
            return 0;
        }

        // 출금
        accountService.withdraw(
                tradingHistoryDTO.getSendingAccount(),
                tradingHistoryDTO.getAmount());

        // 입금
        accountService.deposit(
                tradingHistoryDTO.getReceivingAccount(),
                tradingHistoryDTO.getAmount());

        // 성공 처리
        tradingHistoryDTO.setIsFail(false);
        tradingHistoryDTO.setFailMsg(null);

        // 거래내역 저장
        tradingHistoryService.insertTradingHistory(tradingHistoryDTO);

        return 1;
    }
}