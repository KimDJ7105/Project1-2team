package com.exam.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

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
    @Min(value = 1, message = "나이는 1세 이상이어야 합니다")
    private int age;
    private int loginFailCount;

    @NotBlank(message = "생년월일을 선택해 주세요")
    private String birth;

    public void calculateAgeFromBirth() {
        if (this.birth == null || this.birth.isBlank()) return;

        // "2026-06-27" 포맷 파싱 가동
        java.time.LocalDate birthDate = java.time.LocalDate.parse(this.birth);
        java.time.LocalDate currentDate = java.time.LocalDate.now();

        this.age = java.time.Period.between(birthDate, currentDate).getYears();
    }


    public UserDTO() {
    }

    public UserDTO(int userId, String username, String password, String name, LocalDateTime createdAt, int age, int loginFailCount) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.createdAt = createdAt;
        this.age = age;
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
                ", age=" + age +
                ", loginFailCount=" + loginFailCount +
                ", birth='" + birth + '\'' +
                '}';
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
        if (birth != null && !birth.isBlank()) {
            calculateAgeFromBirth(); // 나이 입력
        }
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getLoginFailCount() {
        return loginFailCount;
    }

    public void setLoginFailCount(int loginFailCount) {
        this.loginFailCount = loginFailCount;
    }
}