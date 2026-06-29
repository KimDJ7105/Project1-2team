package com.exam.service;

import com.exam.dto.TradingHistoryDTO;

import java.util.List;

public interface TradingHistoryService {
    List<TradingHistoryDTO> tradingHistory(int accountId);

    // 기존 송금 이력 개수 조회
    int countTransferHistory(int sendingAccount, int receivingAccount);

    // 최근 10분 내 송금 횟수 조회
    int countRecentTransfer(int sendingAccount);

    // 거래 기록 저장
    int insertTradingHistory(TradingHistoryDTO tradingHistoryDTO);
}
