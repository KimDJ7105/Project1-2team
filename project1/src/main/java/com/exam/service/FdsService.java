package com.exam.service;

import com.exam.dto.AccountDTO;
import com.exam.dto.FdsDTO;
import com.exam.dto.TradingHistoryDTO;

import java.util.List;

public interface FdsService {
    List<FdsDTO> findFds(int accountId);

    //거래금액, 거래시간, 신규 계좌 여부, 반복 거래, 잔액 대비 거래 비율, 해외 거래 여부
    FdsDTO calculateFDS(TradingHistoryDTO tradingHistoryDTO, AccountDTO accountDTO);

    // 계산된 FDS 저장
    int insertFds(FdsDTO fdsDTO);
}
