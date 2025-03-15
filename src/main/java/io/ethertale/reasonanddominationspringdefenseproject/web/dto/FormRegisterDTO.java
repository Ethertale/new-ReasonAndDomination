package io.ethertale.reasonanddominationspringdefenseproject.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FormRegisterDTO {
    @NotBlank(message = "Username cannot be empty")
    @Size(min = 5, max = 15, message = "Username must be between 5 and 15 characters")
    private String username;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Not a valid email format")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank
    private String confirmPassword;

}
