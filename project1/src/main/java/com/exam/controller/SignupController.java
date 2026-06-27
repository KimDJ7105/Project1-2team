package com.exam.controller;

import com.exam.dto.UserDTO;
import com.exam.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller

public class SignupController {

    UserService userService;
    PasswordEncoder passwordEncoder;
    public SignupController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/signup")
    public String signForm(Model model) {
        model.addAttribute("userDto", new UserDTO());
        return "signupForm";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("userDto") UserDTO userDto, BindingResult result) {
        if(result.hasErrors()) { //검증 실패시
            System.out.println(result.getAllErrors());
            return "signupForm";
        }

        userDto.calculateAgeFromBirth();

        // 비밀번호 암호화
        String originalPassword = userDto.getPassword();
        userDto.setPassword(passwordEncoder.encode(originalPassword));

        int n = userService.signup(userDto);
        if(n > 0) System.out.println("회원가입 완료");
        else { // 회원가입 실패
            result.rejectValue("username", "duplicate", "이미 사용 중인 아이디입니다.");
            return "signupForm";
        }

        //성공하면 로그인 화면으로 이동
        return "redirect:/login";
    }
}
