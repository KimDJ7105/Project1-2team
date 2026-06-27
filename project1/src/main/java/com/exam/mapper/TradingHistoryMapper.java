package com.exam.mapper;

import com.exam.dto.TradingHistoryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TradingHistoryMapper {
    List<TradingHistoryDTO>tradingHistory(int accountId);
}
