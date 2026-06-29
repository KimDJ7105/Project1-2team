package com.exam.mapper;

import com.exam.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int signup(UserDTO dto);
    UserDTO login(String username);
    UserDTO findUser(int userId);
}
