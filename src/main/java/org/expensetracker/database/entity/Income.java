package org.expensetracker.database.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Income {
    private Long id;
    private BigDecimal amount;
    private Long incomeSourceId;
    private LocalDate date;

    public Income() {
    }

    public Income(Long id, BigDecimal amount, Long incomeSourceId, LocalDate localDate) {
        this.id = id;
        this.amount = amount;
        this.incomeSourceId = incomeSourceId;
        this.date = localDate;
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

    public Long getIncomeSourceId() {
        return incomeSourceId;
    }

    public void setIncomeSourceId(Long incomeSourceId) {
        this.incomeSourceId = incomeSourceId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Income income = (Income) o;
        return Objects.equals(id, income.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Income{" +
                "id=" + id +
                ", amount=" + amount +
                ", incomeSourceId=" + incomeSourceId +
                ", date=" + date +
                '}';
    }
}
