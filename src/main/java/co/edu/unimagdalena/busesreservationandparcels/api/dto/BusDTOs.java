package co.edu.unimagdalena.busesreservationandparcels.api.dto;

import co.edu.unimagdalena.busesreservationandparcels.domain.enums.BusStatus;
import co.edu.unimagdalena.busesreservationandparcels.api.dto.AmenityDTOs.AmenityResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Set;

public class BusDTOs {
    public record BusCreateRequest(@NotBlank @Size(min = 6) String plate, @NotBlank @Size(max = 4) String number,
                                   @Positive Integer capacity, BusStatus status,
                                   @NotNull Set<Long> amenityIds) implements Serializable {
    }

    public record BusUpdateRequest(@Size(max = 4) String number, @Positive Integer capacity,
                                   BusStatus status, Set<Long> amenityIds) implements Serializable {
    }

    public record BusResponse(Long id, String plate, String number, Integer capacity, BusStatus status,
                              Set<AmenityResponse> amenities) implements Serializable {
    }
}
