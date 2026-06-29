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

        UserDTO existingUser = login(dto.getUsername()); // 로그인용 메서드 재활용
        if (existingUser != null) {
            // 입력한 username 값이 이미 존재하는 경우.
            return 0;
        }

        return memberMapper.signup(dto);
    }

    @Override
    public UserDTO login(String username) {
        return memberMapper.login(username);
    }

    @Override
    public UserDTO findUser(int userId) {return memberMapper.findUser(userId);}
}
