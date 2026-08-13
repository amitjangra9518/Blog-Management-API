package blog.payloads;

import blog.enums.Role;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private int id;

    @NotBlank(message = "Name is required")
    @Size(min = 4, message = "Name must be at least 4 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(
            min = 6,
            max = 20,
            message = "Password must be between 6 and 20 characters"
    )
    private String password;

    @NotBlank(message = "About is required")
    @Size(
            min = 10,
            max = 100,
            message = "About must be between 10 and 100 characters"
    )
    private String about;

    // User role
    private Role role;
}