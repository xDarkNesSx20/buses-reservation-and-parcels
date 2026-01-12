package co.edu.unimagdalena.busesreservationandparcels.api.dto;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.CommonResponses.StopSummary;
import co.edu.unimagdalena.busesreservationandparcels.api.dto.CommonResponses.TripSummary;
import co.edu.unimagdalena.busesreservationandparcels.api.dto.CommonResponses.UserSummary;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.PaymentMethod;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.TicketStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;

public class TicketDTOs {
    public record TicketCreateRequest(@NotNull Long passengerId, @NotBlank @Size(min = 2, max = 5) String seatNumber,
                                      @NotNull Long fromStopId, @NotNull Long toStopId,
                                      @NotNull PaymentMethod paymentMethod) implements Serializable {
    }

    public record TicketUpdateRequest(@Size(min = 2, max = 5) String seatNumber, Long toStopId,
                                      PaymentMethod paymentMethod, TicketStatus status) implements Serializable {
    }

    public record TicketResponse(Long id, Long tripId, Long passengerId, String seatNumber,
                                 StopSummary fromStop, StopSummary toStop, BigDecimal price,
                                 PaymentMethod paymentMethod, TicketStatus status) implements Serializable {
    }

    //Response with trip details
    public record TicketWTResponse(Long id, String seatNumber, Long passengerId, TripSummary trip,
                                   StopSummary fromStop, StopSummary toStop, BigDecimal price,
                                   PaymentMethod paymentMethod, TicketStatus status) implements Serializable {
    }

    //Response with passenger details
    public record TicketWPResponse(Long id, String seatNumber, Long tripId, UserSummary passenger,
                                   StopSummary fromStop, StopSummary toStop, BigDecimal price,
                                   PaymentMethod paymentMethod, TicketStatus status) implements Serializable {
    }

    public record TicketFullResponse(Long id, String seatNumber, TripSummary trip, UserSummary passenger,
                                     StopSummary fromStop, StopSummary toStop, BigDecimal price,
                                     PaymentMethod paymentMethod, TicketStatus status) implements Serializable {
    }
}
