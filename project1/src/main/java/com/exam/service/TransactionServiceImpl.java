package com.exam.service;

import com.exam.dto.AccountDTO;
import com.exam.dto.FdsDTO;
import com.exam.dto.TradingHistoryDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {

    AccountService accountService;
    TradingHistoryService tradingHistoryService;
    FdsService fdsService;

    public TransactionServiceImpl(AccountService accountService, TradingHistoryService tradingHistoryService, FdsService fdsService) {
        this.accountService = accountService;
        this.tradingHistoryService = tradingHistoryService;
        this.fdsService = fdsService;
    }

    @Override
    @Transactional
    public int sendMoneyProcess(TradingHistoryDTO tradingHistoryDTO) {

        // 출금 계좌 조회
        AccountDTO sendingAccount = accountService.findAccountById(tradingHistoryDTO.getSendingAccount());

        // 입금 계좌 조회
        AccountDTO receivingAccount = accountService.findAccountById(tradingHistoryDTO.getReceivingAccount());

        // 출금 계좌 없는 경우
        if (sendingAccount == null) {
            tradingHistoryDTO.setIsFail(true);
            tradingHistoryDTO.setFailMsg("입금 계좌가 존재하지 않습니다.");
            tradingHistoryService.insertTradingHistory(tradingHistoryDTO);
            return 0;
        }

        // 입금 계좌가 없는 경우
        if (receivingAccount == null) {
            tradingHistoryDTO.setIsFail(true);
            tradingHistoryDTO.setFailMsg("입금 계좌가 존재하지 않습니다.");
            tradingHistoryService.insertTradingHistory(tradingHistoryDTO);
            return 0;
        }

        // 잔액 부족 확인
        if (sendingAccount.getBalance() < tradingHistoryDTO.getAmount()) {
            tradingHistoryDTO.setIsFail(true);
            tradingHistoryDTO.setFailMsg("잔액이 부족합니다.");
            tradingHistoryService.insertTradingHistory(tradingHistoryDTO);
            return 0;
        }

        // FDS 검사
        FdsDTO fdsDTO = fdsService.calculateFDS(tradingHistoryDTO, sendingAccount);

        // 출금 처리
        accountService.withdraw(tradingHistoryDTO.getSendingAccount(), tradingHistoryDTO.getAmount());

        // 입금 처리
        accountService.deposit(tradingHistoryDTO.getReceivingAccount(), tradingHistoryDTO.getAmount());

        // 거래 성공 처리
        tradingHistoryDTO.setIsFail(false);
        tradingHistoryDTO.setFailMsg(null);

        // 거래 기록 저장
        tradingHistoryService.insertTradingHistory(tradingHistoryDTO);

        // 거래 기록 저장 후 생성된 historyId를 FDS에 넣기
        fdsDTO.setHistoryId(tradingHistoryDTO.getHistoryId());

        // FDS 결과 저장
        fdsService.insertFds(fdsDTO);
        return 1;
    }
}
