package co.edu.unimagdalena.busesreservationandparcels.api.dto;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.CommonResponses.UserSummary;
import co.edu.unimagdalena.busesreservationandparcels.api.dto.CommonResponses.TripSummary;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.SeatHoldStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class SeatHoldDTOs {
    public record SeatHoldCreateRequest(@NotBlank @Size(min = 2, max = 5) String seatNumber,
                                        @NotNull Long passengerId) implements Serializable {
    }

    public record SeatHoldUpdateRequest(@Size(min = 2, max = 5) String seatNumber,
                                        SeatHoldStatus status) implements Serializable {
    }

    public record SeatHoldResponse(Long id, Long tripId, String seatNumber, Long passengerId,
                                   OffsetDateTime expiresAt, SeatHoldStatus status) implements Serializable {
    }

    //Response with trip details
    public record SeatHoldWTResponse(Long id, TripSummary trip, String seatNumber, Long passengerId,
                                     OffsetDateTime expiresAt, SeatHoldStatus status) implements Serializable {
    }

    //Response with passenger details
    public record SeatHoldWPResponse(Long id, Long tripId, String seatNumber, UserSummary passenger,
                                     OffsetDateTime expiresAt, SeatHoldStatus status) implements Serializable {
    }

    public record SeatHoldFullResponse(Long id, TripSummary trip, String seatNumber, UserSummary passenger,
                                       OffsetDateTime expiresAt, SeatHoldStatus status) implements Serializable {
    }
}
