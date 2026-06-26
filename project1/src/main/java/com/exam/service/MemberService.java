package com.exam.service;

import com.exam.dto.MemberDTO;

public interface MemberService {
    int signup(MemberDTO dto);
    MemberDTO findById(String userid);
}
