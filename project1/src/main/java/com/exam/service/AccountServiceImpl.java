package com.exam.service;

import com.exam.dto.AccountDTO;
import com.exam.mapper.AccountMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AccountServiceImpl implements AccountService {

    AccountMapper accountMapper;

    public AccountServiceImpl(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @Override
    public List<AccountDTO> findAccount(int userId) { return accountMapper.findAccount(userId);}

    @Override
    public AccountDTO findAccountById(int accountId) { return accountMapper.findAccountById(accountId);}

    @Override
    public int deposit(int accountId, int amount) {return  accountMapper.deposit(accountId,amount);}

    @Override
    public int insertAccount(AccountDTO accountDTO) {
        // 랜덤 계좌 생성
        String bankCode = "123";
        int middleNumber = ThreadLocalRandom.current().nextInt(100, 1000);
        int lastNumber = ThreadLocalRandom.current().nextInt(10000, 100000);
        String account = bankCode + middleNumber + lastNumber;

        accountDTO.setAccountNumber(account);

        //랜덤 계좌번호 생성
        return accountMapper.insertAccount(accountDTO);
    }

    @Override
    public int withdraw(int accountId, int amount) {return accountMapper.withdraw(accountId,amount);}

    @Override
    public AccountDTO findAccountByAccountNumber(String accountNumber) {return accountMapper.findAccountByAccountNumber(accountNumber);}
}
