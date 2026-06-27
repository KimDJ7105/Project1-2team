package com.exam.mapper;

import com.exam.dto.TradingHistoryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TradingHistoryMapper {
    List<TradingHistoryDTO>tradingHistory(int accountId);

    //기존 송금 이력 개수 조회
    int countTransferHistory(int sendingAccount, int receivingAccount);

    // 최근 10분 내 송금 횟수 조회
    int countRecentTransfer(int sendingAccount);
}
