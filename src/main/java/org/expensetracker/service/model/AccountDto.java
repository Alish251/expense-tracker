package org.expensetracker.service.model;

import java.math.BigDecimal;
import java.util.Objects;

public class AccountDto {
    private BigDecimal balance;
    private Long userId;

    public AccountDto() {
    }

    public AccountDto(BigDecimal balance, Long userId) {
        this.balance = balance;
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDto that = (AccountDto) o;
        return Objects.equals(balance, that.balance) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(balance, userId);
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "balance=" + balance +
                ", userId=" + userId +
                '}';
    }
}
