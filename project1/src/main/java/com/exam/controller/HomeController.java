package com.exam.controller;

import com.exam.dto.AccountDTO;
import com.exam.dto.UserDTO;
import com.exam.service.AccountService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@SessionAttributes(value = {"my_login"})
public class HomeController {

    AccountService accountService;
    public HomeController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping("/home")
    public String  home(HttpSession session, Model model){
        UserDTO loginUser = (UserDTO) session.getAttribute("myLogin");

        if (loginUser == null) {
            return "home";
        }

        List<AccountDTO> accountList = accountService.findAccount(loginUser.getUserId());
        model.addAttribute("accounts", accountList);

        return "home";
    }

}
