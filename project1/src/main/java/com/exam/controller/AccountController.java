package com.exam.controller;

import com.exam.dto.AccountDTO;
import com.exam.dto.UserDTO;
import com.exam.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String showCreatedAccount(HttpServletRequest request, Model model) {
//        Map<String, ?> flashMap = org.springframework.web.servlet.support.RequestContextUtils.getInputFlashMap(request);
//
//        if (flashMap != null) {
//            // 2. 포스트 단에서 담았던 진짜 알맹이들을 수신합니다.
//            String userName = (String) flashMap.get("userName");
//            String newAccount = (String) flashMap.get("newAccount");
//
//            // 3. 타임리프 화면 단까지 완벽하게 도달할 수 있도록 Model 상자에 패킹합니다.
//            model.addAttribute("userName", userName);
//            model.addAttribute("newAccount", newAccount);
//        }
        return "accountAdd";
    }

    @PostMapping("/createAccount")
    public String createAccount( HttpSession session, RedirectAttributes rttr ) {
        UserDTO loginUser = (UserDTO) session.getAttribute("myLogin");

        //로그인 안되어 있으면 로그인 페이지로 가기
        if (loginUser == null) {
            return "redirect:/login";
        }

        //계좌 생성
        AccountDTO dto = new AccountDTO();
        dto.setUserId(loginUser.getUserId());
        int n = accountService.insertAccount(dto);
        if(n>0) {
            System.out.println("account inserted");

            // --- 테스트를 위해 시작 금액 지급
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("accountNumber", dto.getAccountNumber());
            paramMap.put("amount", 5000000);
            accountService.deposit(paramMap);


            rttr.addFlashAttribute("newAccount", dto.getAccountNumber());
            rttr.addFlashAttribute("userName", loginUser.getName());
        }
        else {
            // 계좌 생성 실패한 경우. 이후 실패 페이지를 만들거나 예외 처리 필요.
            System.out.println("account insert failed");
        }


        return "redirect:/createAccount";
    }
}
