package com.exam.service;

import com.exam.dto.UserDTO;

public interface UserService {
    int signup(UserDTO dto);
    UserDTO login(String username);
}
