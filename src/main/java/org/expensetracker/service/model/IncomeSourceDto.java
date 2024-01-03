package org.expensetracker.service.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
public class IncomeSourceDto {
    private Long id;
    private String name;
    private String description;
}
