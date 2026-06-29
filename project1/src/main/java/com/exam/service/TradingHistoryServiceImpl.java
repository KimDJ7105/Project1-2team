package com.exam.service;

import com.exam.dto.TradingHistoryDTO;
import com.exam.mapper.TradingHistoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TradingHistoryServiceImpl implements TradingHistoryService {

    TradingHistoryMapper tradingHistoryMapper;

    public TradingHistoryServiceImpl(TradingHistoryMapper tradingHistoryMapper) {
        this.tradingHistoryMapper = tradingHistoryMapper;
    }

    @Override
    public List<TradingHistoryDTO> tradingHistory(int accountId) { return tradingHistoryMapper.tradingHistory(accountId);}

    @Override
    public int insertTradingHistory(TradingHistoryDTO tradingHistoryDTO) {return tradingHistoryMapper.insertTradingHistory(tradingHistoryDTO);}

    @Override
    public int countTransferHistory(int sendingAccount, int receivingAccount) {return tradingHistoryMapper.countTransferHistory(sendingAccount, receivingAccount);}

    @Override
    public int countRecentTransfer(int sendingAccount) {return tradingHistoryMapper.countRecentTransfer(sendingAccount);}
}
