package com.exam.service;

import com.exam.dto.AccountDTO;
import com.exam.mapper.AccountMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    AccountMapper memberMapper;

    public AccountServiceImpl(AccountMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public List<AccountDTO> findAccount(int userId) { return memberMapper.findAccount(userId); }
}
