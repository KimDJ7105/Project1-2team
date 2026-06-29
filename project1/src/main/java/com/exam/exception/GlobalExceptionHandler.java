package com.exam.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import jakarta.servlet.http.HttpSession;

@ControllerAdvice // 🌟 모든 컨트롤러의 찐빠(예외)를 감시하는 관제탑 가드레일 가동
public class GlobalExceptionHandler {

    // 시스템 내부 널 포인터나 DB 에러(500 서버 터짐) 가로채기
    @ExceptionHandler(Exception.class)
    public String handleAllException(Exception ex, Model model) {
        ex.printStackTrace();

        //model.addAttribute("errorMessage", "시스템 내부 연산 중 일시적인 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.");
        return "home"; // 예외 페이지를 만들어 넣어주기.
    }

    // 존재하지 않는 주소 오류
//    @ExceptionHandler(NoResourceFoundException.class)
//    public String handle404Exception() {
//        return "error/404"; // 예외 페이지를 만들어서 넣어주면 좋다.
//    }
}