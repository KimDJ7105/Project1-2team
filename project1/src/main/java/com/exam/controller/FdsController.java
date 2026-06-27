package com.exam.controller;

import com.exam.dto.FdsDTO;
import com.exam.service.FdsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FdsController {

    FdsService fdsService;
    public FdsController(FdsService fdsService) {
        this.fdsService = fdsService;
    }

    // 특정 계좌의 FDS 결과 조회
    @GetMapping("/fds/result")
    public String showFdsResult(@RequestParam int accountId, Model model){

        List<FdsDTO> fdsList = fdsService.findFds(accountId);
        model.addAttribute("fdsList",fdsList);
        return "fds/result";
    }
}
