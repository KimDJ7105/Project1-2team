package com.exam.service;

import com.exam.dto.AccountDTO;
import com.exam.dto.FdsDTO;
import com.exam.dto.TradingHistoryDTO;
import com.exam.mapper.FdsMapper;
import com.exam.service.TradingHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FdsServiceImpl implements FdsService {

    FdsMapper fdsMapper;
    TradingHistoryService tradingHistoryService;

    public FdsServiceImpl(FdsMapper fdsMapper, TradingHistoryService tradingHistoryService) {
       this.fdsMapper = fdsMapper;
       this.tradingHistoryService = tradingHistoryService;
    }


    @Override
    public List<FdsDTO> findFds(int accountId) { return fdsMapper.findFds(accountId); }

    @Override
    public FdsDTO calculateFDS(TradingHistoryDTO tradingHistoryDTO, AccountDTO accountDTO) {
        // 위험 점수
        int score = 0;

        // 위험 사유
        String reason = "";

        // 과거 FDS 이력 가져오기
        List<FdsDTO> fdsList =  fdsMapper.findFds(accountDTO.getAccountId());
        if (fdsList != null) {
            long highRiskCount = fdsList.stream()
                    .filter(dto -> dto.getRiskScore() >= 70)
                    .count();

            if (highRiskCount >= 3) {
                score += 20;
                reason += "-고위험 계좌\n";
            }
        }

        // 거래 금액 가져오기
        int amount = tradingHistoryDTO.getAmount();

        // 계좌 잔액 가져오기
        int balance = accountDTO.getBalance();

        // 거래 금액 검사

        if (amount <= 100000) {

        } else if (amount <= 500000) {
            score +=5;
            reason += "-소액 거래\n";
        } else if (amount <= 1000000) {
            score +=10;
            reason += "-중간 금액\n";
        } else if (amount <= 3000000) {
            score +=20;
            reason += "-고액 거래\n";
        } else {
            score +=35;
            reason += "-매우 고액 거래\n";
        }

        // 거래 시간

        int hour = tradingHistoryDTO.getTradeDate().getHour();

        if (hour >= 6 && hour < 22) {

        } else if (hour >= 22) {
            score +=10;
            reason += "-야간 거래\n";
        } else {
            score +=20;
            reason += "-심야 거래\n";
        }

        //신규 계좌 여부 (기존 송금 이력 있는지 없는지 검사)
        int transferHistorycount=tradingHistoryService.countTransferHistory(tradingHistoryDTO.getSendingAccount(), tradingHistoryDTO.getReceivingAccount());
        if (transferHistorycount==0) {
            score +=20;
            reason += "-신규 계좌\n";
        } else {

        }

        //반복 거래
        int recentTransferCount = tradingHistoryService.countRecentTransfer(tradingHistoryDTO.getSendingAccount());

        if (recentTransferCount<=1) {

        } else if (recentTransferCount<=3) {
            score +=10;
            reason += "-반복 거래\n";
        } else {
            score +=20;
            reason += "-과도한 반복 거래\n";
        }



        // 잔액 대비 거래 비율 검사
        if (balance > 0) {
            double ratio = (double) amount / balance;
            if (ratio <= 0.3) {

            } else if (ratio <= 0.7) {
                score +=10;
                reason += "-큰 금액\n";
            } else {
                score +=20;
                reason += "-대부분 인출\n";
            }

            //해외 거래 ???? 일단 보류하겠습니다.
        }

        // 최종 위험 등급 계산
        String rank = "";

        if (score >= 70) {
            rank = "위험";
        } else if (score >= 40) {
            rank = "주의";
        } else {
            rank = "정상";
        }

        if (reason.equals("")) {
            reason = "위험 요소 없음";
        }

        // FDS 결과 DTO 생성
        FdsDTO fdsDTO = new FdsDTO();
        fdsDTO.setAccountId(accountDTO.getAccountId());
        fdsDTO.setRiskScore(score);
        fdsDTO.setRiskRank(rank);
        fdsDTO.setRiskReason(reason);

        return fdsDTO;

    }

    @Override
    public int insertFds(FdsDTO fdsDTO) {
        return fdsMapper.insertFds(fdsDTO);
    }

    @Override
    public FdsDTO findFdsByHistory(int historyId) {return fdsMapper.findFdsByHistory(historyId);};
}
