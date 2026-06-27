package com.exam.service;

import com.exam.dto.FdsDTO;
import com.exam.mapper.FdsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FdsServiceImpl implements FdsService {

    FdsMapper memberMapper;

    public FdsServiceImpl(FdsMapper memberMapper) {
        this.memberMapper = memberMapper;
    }


    @Override
    public List<FdsDTO> findFds(int accountId) { return memberMapper.findFds(accountId); }
}
