package com.exam.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Alias("AccountDTO")
public class AccountDTO {

    private int accountId;
    private int userId;
    @NotBlank(message = "적어도 한글자 이상 입력하세요")
    private String accountNumber;
    @Min(value = 0, message = "잔액은 0원 이상이어야 합니다.")
    private int balance;
    @NotBlank(message = "적어도 한글자 이상 입력하세요")
    private LocalDateTime createdAt;
    private boolean isBlocked;

    public AccountDTO() {
    }

    public AccountDTO(int accountId, int userId, String accountNumber, int balance, LocalDateTime createdAt, boolean isBlocked) {
        this.accountId = accountId;
        this.userId = userId;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.createdAt = createdAt;
        this.isBlocked = isBlocked;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "accountId=" + accountId +
                ", userId=" + userId +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", createdAt=" + createdAt +
                ", isBlocked=" + isBlocked +
                '}';
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}