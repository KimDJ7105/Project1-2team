package com.exam.service;

import com.exam.dto.UserDTO;
import com.exam.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    UserMapper memberMapper;

    public UserServiceImpl(UserMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    @Transactional
    public int signup(UserDTO dto) {
        return memberMapper.signup(dto);
    }

    @Override
    public UserDTO login(UserDTO dto) {
        return memberMapper.login(dto);
    }
}
