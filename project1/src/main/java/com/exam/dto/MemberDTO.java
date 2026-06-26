package com.exam.dto;

import jakarta.validation.constraints.NotBlank;
import org.apache.ibatis.type.Alias;

import java.time.LocalDate;

@Alias("MemberDTO")
public class MemberDTO{

    @NotBlank(message = "적어도 한글자 이상 입력하세요")
    String userid;
    @NotBlank(message = "적어도 한글자 이상 입력하세요")
    String password;
    @NotBlank(message = "적어도 한글자 이상 입력하세요")
    String username;
    String role;

    public MemberDTO() {}

    public MemberDTO(String userid, String password, String username, String role) {
        this.userid = userid;
        this.password = password;
        this.username = username;
        this.role = role;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "MemberDTO{" +
                "userid='" + userid + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}