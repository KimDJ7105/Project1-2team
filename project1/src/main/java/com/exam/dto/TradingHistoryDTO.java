package com.exam.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Alias("TradingHistoryDTO")
public class TradingHistoryDTO {

    private int historyId;
    @NotBlank(message = "적어도 한글자 이상 입력하세요")
    private String tradeType;
    private int sendingAccount;
    private int receivingAccount;
    @Min(value = 0, message = "거래액은 0원 이상이어야 합니다.")
    private int amount;
    private String message;
    private LocalDateTime tradeDate;
    private boolean isFail;
    private String failMsg;
    
    public TradingHistoryDTO() {
    }

    public TradingHistoryDTO(int historyId, String tradeType, int sendingAccount, int receivingAccount, int amount, String message, LocalDateTime tradeDate, boolean isFail, String failMsg) {
        this.historyId = historyId;
        this.tradeType = tradeType;
        this.sendingAccount = sendingAccount;
        this.receivingAccount = receivingAccount;
        this.amount = amount;
        this.message = message;
        this.tradeDate = tradeDate;
        this.isFail = isFail;
        this.failMsg = failMsg;
    }

    @Override
    public String toString() {
        return "TradingHistoryDTO{" +
                "historyId=" + historyId +
                ", tradeType='" + tradeType + '\'' +
                ", sendingAccount=" + sendingAccount +
                ", receivingAccount=" + receivingAccount +
                ", amount=" + amount +
                ", message='" + message + '\'' +
                ", tradeDate=" + tradeDate +
                ", isFail=" + isFail +
                ", failMsg='" + failMsg + '\'' +
                '}';
    }

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public int getSendingAccount() {
        return sendingAccount;
    }

    public void setSendingAccount(int sendingAccount) {
        this.sendingAccount = sendingAccount;
    }

    public int getReceivingAccount() {
        return receivingAccount;
    }

    public void setReceivingAccount(int receivingAccount) {
        this.receivingAccount = receivingAccount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(LocalDateTime tradeDate) {
        this.tradeDate = tradeDate;
    }

    public boolean isFail() {
        return isFail;
    }

    public void setIsFail(boolean fail) {
        isFail = fail;
    }

    public String getFailMsg() {
        return failMsg;
    }

    public void setFailMsg(String failMsg) {
        this.failMsg = failMsg;
    }
}