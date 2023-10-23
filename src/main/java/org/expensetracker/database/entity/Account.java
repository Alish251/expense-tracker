package org.expensetracker.database.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {
    private Long id;
    private BigDecimal balance;
    private Long incomeId;
    private Long expenseId;
    private Long userId;

    public Account() {
    }

    public Account(Long id, BigDecimal balance, Long incomeId, Long expenseId, Long userId) {
        this.id = id;
        this.balance = balance;
        this.incomeId = incomeId;
        this.expenseId = expenseId;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(Long incomeId) {
        this.incomeId = incomeId;
    }

    public Long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Long expenseId) {
        this.expenseId = expenseId;
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
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", incomeId=" + incomeId +
                ", expenseId=" + expenseId +
                ", userId=" + userId +
                '}';
    }
}
