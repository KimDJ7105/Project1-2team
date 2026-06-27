package com.exam.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Alias("LoginHistoryDTO")
public class LoginHistoryDTO {

    private int historyId;
    private int userId;
    @NotBlank(message = "적어도 한글자 이상 입력하세요")
    private String location;
    @NotBlank(message = "적어도 한글자 이상 입력하세요")
    private String ip;
    @NotBlank(message = "적어도 한글자 이상 입력하세요")
    @Pattern(
            regexp = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$",
            message = "올바른 IP 주소 형식이 아닙니다 (예: 192.168.0.1)"
    )
    private String device;
    private boolean isFail;
    private LocalDateTime loginDate;

    public LoginHistoryDTO() {
    }

    public LoginHistoryDTO(int historyId, int userId, String location, String ip, String device, boolean isFail, LocalDateTime loginDate) {
        this.historyId = historyId;
        this.userId = userId;
        this.location = location;
        this.ip = ip;
        this.device = device;
        this.isFail = isFail;
        this.loginDate = loginDate;
    }

    @Override
    public String toString() {
        return "LoginHistoryDTO{" +
                "historyId=" + historyId +
                ", userId=" + userId +
                ", location='" + location + '\'' +
                ", ip='" + ip + '\'' +
                ", device='" + device + '\'' +
                ", isFail=" + isFail +
                ", loginDate=" + loginDate +
                '}';
    }

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public boolean isFail() {
        return isFail;
    }

    public void setFail(boolean fail) {
        isFail = fail;
    }

    public LocalDateTime getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(LocalDateTime loginDate) {
        this.loginDate = loginDate;
    }
}