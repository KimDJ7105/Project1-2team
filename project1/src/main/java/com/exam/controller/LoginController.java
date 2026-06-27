package com.exam.controller;

import com.exam.dto.LoginRequestDTO;
import com.exam.dto.UserDTO;
import com.exam.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


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
            rttr.addFlashAttribute("errorMessage", "아이디와 비밀번호 규격이 올바르지 않습니다.");
            return "redirect:/login";
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

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    DBUser.getUsername(), // 인증 식별자
                    null,
                    List.of(new SimpleGrantedAuthority("ROLE_USER")) // 권한 등급
            );

            // 시큐리티 글로벌 메모리에 티켓 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            return "redirect:/home";
        }

        //로그인 실패
        rttr.addFlashAttribute("errorMessage", "아이디 또는 비밀번호가 틀렸습니다.");
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 폭파
        SecurityContextHolder.clearContext(); // 스프링 시큐리티 인증 티켓 폐기
        return "redirect:/login";
    }
}
