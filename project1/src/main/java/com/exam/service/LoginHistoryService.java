package com.exam.service;

import com.exam.dto.LoginHistoryDTO;

import java.util.List;

public interface LoginHistoryService {
    List<LoginHistoryDTO> findLoginHistory(int userId);
}
