package co.edu.unimagdalena.busesreservationandparcels.api.dto;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.CommonResponses.BusSummary;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.SeatType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class SeatDTOs {
    public record SeatCreateRequest(@NotBlank @Size(min = 2, max = 5) String number,
                                    SeatType type) implements Serializable {
    }

    public record SeatUpdateRequest(String number, SeatType type) implements Serializable {}

    public record SeatResponse(Long id, Long busId, String number, SeatType type) implements Serializable {}

    public record SeatFullResponse(Long id, BusSummary bus, String number, SeatType type) implements Serializable {}
}
