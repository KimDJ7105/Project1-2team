package com.exam.controller;

import com.exam.dto.TradingHistoryDTO;
import com.exam.service.AccountService;
import com.exam.service.FdsService;
import com.exam.service.TradingHistoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TransactionController {

    AccountService accountService;
    TradingHistoryService tradingHistoryService;
    FdsService fdsService;

    public TransactionController(AccountService accountService, TradingHistoryService tradingHistoryService, FdsService fdsService) {
        this.accountService = accountService;
        this.tradingHistoryService = tradingHistoryService;
        this.fdsService = fdsService;
    }

    // 계좌 이체 화면 요청
    @GetMapping("/sendMoney")
    public String sendMoney(Model model) {

        model.addAttribute("tradingHistoryDTO", new TradingHistoryDTO());
        return"sendMoney";
    }

    // 계좌 이체 처리
    @PostMapping("/sendMoney")
    public String sendMoneyProcess(TradingHistoryDTO tradingHistoryDTO, Model model) {
        //출금 계좌
        //잔액 확인
        //FDS 검사
        //송금 처리
        //거래 기록 저장하기

        return "redirect:/home";

    }
}
