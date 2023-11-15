package org.expensetracker.service.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class ExpenseDto {
    private Long id;
    private BigDecimal amount;
    private LocalDate date;
    private Long categoryId;
    private Long accountId;

    public ExpenseDto() {
    }

    public ExpenseDto(Long id, BigDecimal amount, LocalDate date, Long categoryId, Long accountId) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.categoryId = categoryId;
        this.accountId = accountId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ExpenseDto{" +
                "id=" + id +
                ", amount=" + amount +
                ", date=" + date +
                ", categoryId=" + categoryId +
                ", accountId=" + accountId +
                '}';
    }
}
