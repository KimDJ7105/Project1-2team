package com.exam.service;

import com.exam.dto.LoginHistoryDTO;
import com.exam.mapper.LoginHistoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginHistoryImpl implements LoginHistoryService {

    LoginHistoryMapper memberMapper;

    public LoginHistoryImpl(LoginHistoryMapper memberMapper) {
        this.memberMapper = memberMapper;
    }


    @Override
    public List<LoginHistoryDTO> findLoginHistory(int userId) { return memberMapper.findLoginHistory(userId); }
}
