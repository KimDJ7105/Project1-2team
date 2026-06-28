package com.exam.service;

import com.exam.dto.TradingHistoryDTO;

public interface TransactionService {

    // 계좌 이체 처리
    int sendMoneyProcess(TradingHistoryDTO tradingHistoryDTO);
}
