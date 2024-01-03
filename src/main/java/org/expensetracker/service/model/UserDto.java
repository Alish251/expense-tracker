package org.expensetracker.service.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
public class UserDto {
    private Long id;
    @NotEmpty(message = "Firstname should not be empty")
    @Size(min = 2, max = 30)
    private String firstname;
    @NotEmpty(message = "Lastname should not be empty")
    @Size(min = 2, max = 30)
    private String lastname;
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;
    private List<AccountDto> accounts;

}
