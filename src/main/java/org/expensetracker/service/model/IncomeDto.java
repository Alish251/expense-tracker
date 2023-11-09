package org.expensetracker.service.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class IncomeDto {
    private BigDecimal amount;
    private LocalDate date;
    private Long incomeSourceId;
    private Long accountId;

    public IncomeDto() {
    }

    public IncomeDto(BigDecimal amount, LocalDate date, Long incomeSourceId, Long accountId) {
        this.amount = amount;
        this.date = date;
        this.incomeSourceId = incomeSourceId;
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getIncomeSourceId() {
        return incomeSourceId;
    }

    public void setIncomeSourceId(Long incomeSourceId) {
        this.incomeSourceId = incomeSourceId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IncomeDto incomeDto = (IncomeDto) o;
        return Objects.equals(amount, incomeDto.amount) && Objects.equals(date, incomeDto.date) && Objects.equals(incomeSourceId, incomeDto.incomeSourceId) && Objects.equals(accountId, incomeDto.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, date, incomeSourceId, accountId);
    }

    @Override
    public String toString() {
        return "IncomeDto{" +
                "amount=" + amount +
                ", date=" + date +
                ", incomeSourceDto=" + incomeSourceId +
                ", accountDto=" + accountId +
                '}';
    }
}
