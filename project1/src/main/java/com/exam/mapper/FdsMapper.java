package com.exam.mapper;

import com.exam.dto.FdsDTO;
import com.exam.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FdsMapper {
    List<FdsDTO> findFds(int accountId);
}
