package org.expensetracker.service.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class ExpenseDto {
    private BigDecimal amount;
    private LocalDate date;
    private Long categoryId;
    private Long accountId;

    public ExpenseDto() {
    }

    public ExpenseDto(BigDecimal amount, LocalDate date, Long categoryDto, Long accountId) {
        this.amount = amount;
        this.date = date;
        this.categoryId = categoryDto;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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
        ExpenseDto that = (ExpenseDto) o;
        return Objects.equals(amount, that.amount) && Objects.equals(date, that.date) && Objects.equals(categoryId, that.categoryId) && Objects.equals(accountId, that.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, date, categoryId, accountId);
    }

    @Override
    public String toString() {
        return "ExpenseDto{" +
                "amount=" + amount +
                ", date=" + date +
                ", categoryId=" + categoryId +
                ", accountId=" + accountId +
                '}';
    }
}
