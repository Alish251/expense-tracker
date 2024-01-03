package org.expensetracker.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
public class AccountDto {
    private Long id;
    private BigDecimal balance;
    @JsonIgnoreProperties("accounts")
    private UserDto user;
}
