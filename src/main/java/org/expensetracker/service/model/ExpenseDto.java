package org.expensetracker.service.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
public class ExpenseDto {
    private Long id;
    private BigDecimal amount;
    private LocalDate date;
    private Long categoryId;
    private Long accountId;
}
