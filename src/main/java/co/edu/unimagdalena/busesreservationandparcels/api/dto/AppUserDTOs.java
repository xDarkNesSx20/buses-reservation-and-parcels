package co.edu.unimagdalena.busesreservationandparcels.api.dto;

import co.edu.unimagdalena.busesreservationandparcels.domain.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Set;

public class AppUserDTOs {
    public record AppUserCreateRequest(@NotBlank String fullName, @NotBlank @Size(min = 10) String phone,
                                       @Email String email, @NotBlank @Size(min = 8) String password,
                                       @NotNull Role role) implements Serializable {
    }

    public record AppUserUpdateRequest(String fullName, String phone,
                                       @Email String email, String password) implements Serializable {
    }

    public record AppUserResponse(Long id, String fullName, String phone, String email, Boolean active,
                                  OffsetDateTime createdAt, Set<Role> roles) implements Serializable {
    }
}
