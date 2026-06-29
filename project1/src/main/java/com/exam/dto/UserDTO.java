package com.exam.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.apache.ibatis.type.Alias;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@Alias("UserDTO")
public class UserDTO {

    private int userId;
    @NotBlank(message = "적어도 한글자 이상 입력하세요")
    private String username;
    @NotBlank(message = "적어도 한글자 이상 입력하세요")
    private String password;
    @NotBlank(message = "적어도 한글자 이상 입력하세요")
    private String name;
    private LocalDateTime createdAt;
    @NotBlank(message = "생년월일을 선택해 주세요")
    private String birthDate;
    private int loginFailCount;




    public UserDTO() {
    }

    public UserDTO(int userId, String username, String password, String name, LocalDateTime createdAt, String birthDate, int loginFailCount) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.createdAt = createdAt;
        this.birthDate = birthDate;
        this.loginFailCount = loginFailCount;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", birthDate='" + birthDate + '\'' +
                ", loginFailCount=" + loginFailCount +
                '}';
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getLoginFailCount() {
        return loginFailCount;
    }

    public void setLoginFailCount(int loginFailCount) {
        this.loginFailCount = loginFailCount;
    }
}