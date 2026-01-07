package co.edu.unimagdalena.busesreservationandparcels.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class OfflineUserDTOs {
    public record OfflineUserCreateRequest(@NotBlank String fullName, @NotBlank @Size(min = 10) String phone,
                                           @NotBlank @Size(min = 8, max = 10) String idNumber) implements Serializable {
    }

    public record AppUserUpdateRequest(String fullName, String phone) implements Serializable {
    }

    public record AppUserResponse(Long id, String fullName, String phone, String idNumber,
                                  OffsetDateTime createdAt) implements Serializable {
    }
}
