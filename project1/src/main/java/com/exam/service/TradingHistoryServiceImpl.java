package com.exam.service;

import com.exam.dto.TradingHistoryDTO;
import com.exam.mapper.TradingHistoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TradingHistoryServiceImpl implements TradingHistoryService {

    TradingHistoryMapper memberMapper;

    public TradingHistoryServiceImpl(TradingHistoryMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public List<TradingHistoryDTO> tradingHistory(int accountId) { return memberMapper.tradingHistory(accountId); }
}
