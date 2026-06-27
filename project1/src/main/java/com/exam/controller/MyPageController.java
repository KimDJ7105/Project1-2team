package com.exam.controller;

import com.exam.dto.AccountDTO;
import com.exam.dto.FdsDTO;
import com.exam.dto.UserDTO;
import com.exam.service.AccountService;
import com.exam.service.FdsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MyPageController {

    AccountService accountService;
    public MyPageController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/mypage")
    public String mypage(HttpSession session, Model model) {
        // 로그인 확인
        UserDTO loginUser = (UserDTO) session.getAttribute("myLogin");

        // 로그인이 안 되어 있다면 로그인 페이지로
        if (loginUser == null) {
            return "redirect:/login";
        }

        // 로그인 상태가 확인되면 계좌 정보 전송
        List<AccountDTO> accountList = accountService.findAccount(loginUser.getUserId());
        model.addAttribute("accounts", accountList);

        return "mypage";
    }
}
