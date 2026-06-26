package com.exam.mapper;

import com.exam.dto.LoginHistoryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoginHistoryMapper {
    List<LoginHistoryDTO> findLoginHistory(int userId);
}
