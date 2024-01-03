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
public class IncomeDto {
    private Long id;
    private BigDecimal amount;
    private LocalDate date;
    private Long incomeSourceId;
    private Long accountId;
}
