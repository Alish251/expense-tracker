package org.expensetracker.service.model;

import java.util.Objects;
import java.util.Set;

public class UserDto {
    private String firstname;
    private String lastname;
    private String email;
    private Set<AccountDto> accounts;

    public UserDto() {
    }

    public UserDto(String firstname, String lastname, String email, Set<AccountDto> accounts) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.accounts = accounts;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<AccountDto> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<AccountDto> accounts) {
        this.accounts = accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(firstname, userDto.firstname) && Objects.equals(lastname, userDto.lastname) && Objects.equals(email, userDto.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname, email);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
