package com.exam.service;

import com.exam.dto.FdsDTO;

import java.util.List;

public interface FdsService {
    List<FdsDTO> findFds(int accountId);
}
