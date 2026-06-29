package com.exam.service;

import com.exam.dto.AccountDTO;
import com.exam.dto.FdsDTO;
import com.exam.dto.TradingHistoryDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
public class TransactionServiceImpl implements TransactionService {

    AccountService accountService;
    TradingHistoryService tradingHistoryService;
    UserService userService;
    FdsService fdsService;

    public TransactionServiceImpl(AccountService accountService,
                                  TradingHistoryService tradingHistoryService,
                                  UserService userService,
                                  FdsService fdsService) {
        this.accountService = accountService;
        this.tradingHistoryService = tradingHistoryService;
        this.userService = userService;
        this.fdsService = fdsService;
    }

    @Override
    @Transactional
    public FdsDTO sendMoneyProcess(TradingHistoryDTO tradingHistoryDTO) {
        
        // 가져온 데이터 : 보내는 계좌 id, 목표 계좌 번호, 금액, 메시지
        
        // 거래 시간 저장 (FDS에서 사용할 예정)
        tradingHistoryDTO.setTradeDate(LocalDateTime.now());


        // 출금 계좌 조회
        AccountDTO sendingAccount =
                accountService.findAccountById(tradingHistoryDTO.getSendingAccount());

        // 출금 계좌 없는 경우
        if (sendingAccount == null) {
            tradingHistoryDTO.setIsFail(true);
            tradingHistoryDTO.setFailMsg("출금 계좌가 존재하지 않습니다.");
            tradingHistoryService.insertTradingHistory(tradingHistoryDTO);
            FdsDTO fdsDTO = new FdsDTO();
            fdsDTO.setFdsId(-1);
            return fdsDTO;
        }

        //보내는 사람 기록 (메시지가 없으면 이름으로 설정)
        if(tradingHistoryDTO.getMessage() == null || tradingHistoryDTO.getMessage().equals("")) {
            tradingHistoryDTO.setMessage(userService.findUser(sendingAccount.getUserId()).getName());
        }

        // 계좌번호로 입금 계좌 조회
        AccountDTO receivingAccount =
                accountService.findAccountByAccountNumber(
                        tradingHistoryDTO.getReceivingAccountNumber());


        // 입금 계좌 없는 경우
        if (receivingAccount == null) {
            tradingHistoryDTO.setIsFail(true);
            tradingHistoryDTO.setFailMsg("입금 계좌가 존재하지 않습니다.");
            tradingHistoryService.insertTradingHistory(tradingHistoryDTO);
            FdsDTO fdsDTO = new FdsDTO();
            fdsDTO.setFdsId(-1);
            return fdsDTO;
        }

        // 조회한 accountId 저장
        tradingHistoryDTO.setReceivingAccount(receivingAccount.getAccountId());

        // 잔액 부족 확인
        if (sendingAccount.getBalance() < tradingHistoryDTO.getAmount()) {
            tradingHistoryDTO.setIsFail(true);
            tradingHistoryDTO.setFailMsg("잔액이 부족합니다.");
            tradingHistoryService.insertTradingHistory(tradingHistoryDTO);
            FdsDTO fdsDTO = new FdsDTO();
            fdsDTO.setFdsId(-1);
            return fdsDTO;
        }

        FdsDTO fdsDTO = fdsService.calculateFDS(tradingHistoryDTO, receivingAccount);
        if(fdsDTO.getRiskScore() >= 40 && !tradingHistoryDTO.isFdsBypass()) {
            tradingHistoryDTO.setIsFail(true);
            tradingHistoryDTO.setFailMsg("FDS_RISK_DETECTED");
            tradingHistoryService.insertTradingHistory(tradingHistoryDTO);

            return fdsDTO;
        }

        // 출금
        HashMap<String,Object> withdrawMap = new HashMap<>();
        withdrawMap.put("accountNumber",sendingAccount.getAccountNumber());
        withdrawMap.put("amount",tradingHistoryDTO.getAmount());
        accountService.withdraw(withdrawMap);

        // 입금
        HashMap<String,Object> depositMap = new HashMap<>();
        depositMap.put("accountNumber",tradingHistoryDTO.getReceivingAccountNumber());
        depositMap.put("amount",tradingHistoryDTO.getAmount());
        accountService.deposit(depositMap);

        // 성공 처리
        tradingHistoryDTO.setIsFail(false);
        tradingHistoryDTO.setFailMsg(null);

        // 거래내역 저장
        tradingHistoryService.insertTradingHistory(tradingHistoryDTO);

        if(fdsDTO.getRiskScore() >= 40) { //주의 이상인 경우 DB에 저장.
            fdsDTO.setHistoryId(tradingHistoryDTO.getHistoryId());
            fdsService.insertFds(fdsDTO);
        }

        return fdsDTO;
    }
}