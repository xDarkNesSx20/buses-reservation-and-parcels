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
    public record TicketAppCreateRequest(@NotNull Long passengerId, @NotBlank @Size(min = 2, max = 5) String seatNumber,
                                         @NotNull Long fromStopId, @NotNull Long toStopId,
                                         @NotNull PaymentMethod paymentMethod) implements Serializable {
    }

    public record TicketOfficeCreateRequest(@NotBlank String passengerIdNumber, String passengerFullName, String passengerPhone,
                                            @NotBlank @Size(min = 2, max = 5) String seatNumber,
                                         @NotNull Long fromStopId, @NotNull Long toStopId,
                                         @NotNull PaymentMethod paymentMethod) implements Serializable {
    }

    public record TicketUpdateRequest(@Size(min = 2, max = 5) String seatNumber, Long toStopId,
                                      PaymentMethod paymentMethod, TicketStatus status) implements Serializable {
    }

    public record TicketBasicResponse(Long id, Long tripId, Long passengerId, String seatNumber,
            Long fromStopId, Long toStopId, BigDecimal price,
            PaymentMethod paymentMethod, TicketStatus status, String qrCode) implements Serializable {
    }

    public record TicketResponse(Long id, Long tripId, Long passengerId, String seatNumber,
            StopSummary fromStop, StopSummary toStop, BigDecimal price,
            PaymentMethod paymentMethod, TicketStatus status, String qrCode) implements Serializable {
    }

    public record TicketWithTripResponse(Long id, TripSummary trip, Long passengerId,
            String seatNumber, StopSummary fromStop, StopSummary toStop, BigDecimal price,
            PaymentMethod paymentMethod, TicketStatus status, String qrCode) implements Serializable {
    }

    public record TicketWithPassengerResponse(Long id, Long tripId, UserSummary passenger, String seatNumber,
            StopSummary fromStop, StopSummary toStop, BigDecimal price, PaymentMethod paymentMethod,
            TicketStatus status, String qrCode) implements Serializable {
    }

    public record TicketFullResponse(Long id, TripSummary trip, UserSummary passenger,
            String seatNumber, StopSummary fromStop, StopSummary toStop, BigDecimal price,
            PaymentMethod paymentMethod, TicketStatus status, String qrCode) implements Serializable {
    }
}
