package com.exam.controller;

import com.exam.dto.LoginRequestDTO;
import com.exam.dto.UserDTO;
import com.exam.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller

public class LoginController {

    UserService userService;
    PasswordEncoder passwordEncoder;
    public LoginController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginRequestDto", new LoginRequestDTO());
        return "loginForm";
    }

    @PostMapping("/login")
    public String loginProcess(@Valid @ModelAttribute("loginRequestDto") LoginRequestDTO loginDto, BindingResult bindingResult, RedirectAttributes rttr, HttpSession session) {
        if(bindingResult.hasErrors()) {
            //검증 실패시
            System.out.println(bindingResult.getAllErrors());
            return "loginForm";
        }

        UserDTO DBUser = userService.login(loginDto.getUsername());
        if(DBUser==null) {
            // 없는 아이디를 입력한 것.
            rttr.addFlashAttribute("errorMessage", "아이디 또는 비밀번호가 틀렸습니다.");
            return "redirect:/login";
        }

        if(passwordEncoder.matches(loginDto.getPassword(), DBUser.getPassword())) {
            //로그인 성공
            DBUser.setPassword(""); // 보안을 위해 비밀번호 날려버리기.
            session.setAttribute("myLogin", DBUser);
            return "redirect:/home";
        }

        //로그인 실패
        rttr.addFlashAttribute("errorMessage", "아이디 또는 비밀번호가 틀렸습니다.");
        return "redirect:/login";
    }
}
