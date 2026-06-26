package com.exam.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Alias("FdsDTO")
public class FdsDTO {

    private int fdsId;
    private int historyId;
    private int accountId;
    private String riskRank;
    private LocalDateTime fdsDate;
    private int riskScore;
    private String riskReason;

    public FdsDTO() {
    }

    public FdsDTO(int fdsId, int historyId, int accountId, String riskRank, LocalDateTime fdsDate, int riskScore, String riskReason) {
        this.fdsId = fdsId;
        this.historyId = historyId;
        this.accountId = accountId;
        this.riskRank = riskRank;
        this.fdsDate = fdsDate;
        this.riskScore = riskScore;
        this.riskReason = riskReason;
    }

    @Override
    public String toString() {
        return "FdsDTO{" +
                "fdsId=" + fdsId +
                ", historyId=" + historyId +
                ", accountId=" + accountId +
                ", riskRank='" + riskRank + '\'' +
                ", fdsDate=" + fdsDate +
                ", riskScore=" + riskScore +
                ", riskReason='" + riskReason + '\'' +
                '}';
    }

    public int getFdsId() {
        return fdsId;
    }

    public void setFdsId(int fdsId) {
        this.fdsId = fdsId;
    }

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getRiskRank() {
        return riskRank;
    }

    public void setRiskRank(String riskRank) {
        this.riskRank = riskRank;
    }

    public LocalDateTime getFdsDate() {
        return fdsDate;
    }

    public void setFdsDate(LocalDateTime fdsDate) {
        this.fdsDate = fdsDate;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(int riskScore) {
        this.riskScore = riskScore;
    }

    public String getRiskReason() {
        return riskReason;
    }

    public void setRiskReason(String riskReason) {
        this.riskReason = riskReason;
    }
}