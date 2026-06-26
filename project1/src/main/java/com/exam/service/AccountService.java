package com.exam.service;

import com.exam.dto.AccountDTO;

import java.util.List;

public interface AccountService {
    List<AccountDTO> findAccount(int userId);
}
