package com.exam.controller;

import com.exam.dto.AccountDTO;
import com.exam.dto.UserDTO;
import com.exam.service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AccountController {

    AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // 마이페이지 / 계좌 목록
    @GetMapping("/accounts")
    public String showAccountList(Model model, HttpSession session) {

        UserDTO loginUser = (UserDTO) session.getAttribute("myLogin");


        //로그인 안되어 있으면 로그인 페이지로 가기
        if (loginUser == null) {
            return "redirect:/login";
        }

        List<AccountDTO> accountList = accountService.findAccount(loginUser.getUserId());
        model.addAttribute("accountList", accountList);
        return "accounts";
    }

    // 계좌 추가 화면
    @GetMapping("/createAccount")
    public String createAccount() {
        return "createAccount";
    }
}
