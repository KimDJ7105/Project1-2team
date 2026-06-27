package com.exam.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Alias("LoginReqeustDTO")
public class LoginRequestDTO {

    @NotBlank(message = "적어도 한글자 이상 입력하세요")
    private String username;
    @NotBlank(message = "적어도 한글자 이상 입력하세요")
    private String password;

    public LoginRequestDTO() {
    }

    public LoginRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "LoginRequestDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}