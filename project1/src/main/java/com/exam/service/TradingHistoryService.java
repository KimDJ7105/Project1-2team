package com.exam.service;

import com.exam.dto.TradingHistoryDTO;

import java.util.List;

public interface TradingHistoryService {
    List<TradingHistoryDTO>tradingHistory(int accountId);
}
