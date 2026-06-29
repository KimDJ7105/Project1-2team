package com.exam.controller;

import com.exam.dto.TradingHistoryDTO;
import com.exam.service.AccountService;
import com.exam.service.FdsService;
import com.exam.service.TradingHistoryService;
import com.exam.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TransactionController {

    TransactionService transactionService;
    TradingHistoryService tradingHistoryService;

   public TransactionController(TransactionService transactionService,
                             TradingHistoryService tradingHistoryService) {

    this.transactionService = transactionService;
    this.tradingHistoryService = tradingHistoryService;
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

        // 송금 서비스 실행
        int result = transactionService.sendMoneyProcess(tradingHistoryDTO);

        // 송금 실패
        if (result == 0) {
            model.addAttribute("errorMessage", "송금에 실패했습니다.");
            model.addAttribute("tradingHistoryDTO", tradingHistoryDTO);
            return "sendMoney";
        }

        // 송금 성공
        return "redirect:/home";

    }
// 거래내역 조회
@GetMapping("/transaction/history")
public String showTransactionHistory(int accountId, Model model) {

    model.addAttribute("historyList",
            tradingHistoryService.tradingHistory(accountId));

    return "transactionHistory";
}
}
