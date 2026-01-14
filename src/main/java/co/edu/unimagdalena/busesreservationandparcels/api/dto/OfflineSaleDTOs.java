package co.edu.unimagdalena.busesreservationandparcels.api.dto;

import co.edu.unimagdalena.busesreservationandparcels.domain.enums.PaymentMethod;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.SyncStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class OfflineSaleDTOs {
    public record OfflineSaleCreateRequest(@NotNull Long fromStopId, @NotNull Long toStopId,
                                           @NotBlank String seatNumber, @NotBlank String passengerIdNumber,
                                           String passengerName,
                                           @Size(max = 15) String passengerPhone, @Positive BigDecimal price,
                                           @NotNull PaymentMethod paymentMethod) implements Serializable {
    }

    public record OfflineSaleUpdateRequest(Long toStopId, String seatNumber, String passengerIdNumber,
                                           String passengerPhone, @Positive BigDecimal price,
                                           PaymentMethod paymentMethod) implements Serializable {
    }

    public record OfflineSaleResponse(Long id, Long tripId, Long fromStopId, Long toStopId,
                                      String seatNumber, String passengerIdNumber,
                                      String passengerName, String passengerPhone, BigDecimal price,
                                      PaymentMethod paymentMethod, SyncStatus status, Integer syncAttempts,
                                      OffsetDateTime createdAt, OffsetDateTime syncedAt, Long ticketId) implements Serializable {}
}
