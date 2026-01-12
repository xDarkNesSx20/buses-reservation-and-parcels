package co.edu.unimagdalena.busesreservationandparcels.api.dto;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.CommonResponses.StopSummary;
import co.edu.unimagdalena.busesreservationandparcels.api.dto.CommonResponses.UserSummary;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.ParcelStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;
import java.math.BigDecimal;

public class ParcelDTOs {
    public record ParcelCreateRequest(@NotNull Long fromStopId, @NotNull Long toStopId,
                                      @Positive BigDecimal price, @NotNull Long senderId,
                                      @NotNull Long receiverId) implements Serializable {
    }

    public record ParcelUpdateRequest(@Positive BigDecimal price, ParcelStatus status,
                                      String deliveryOTP) implements Serializable {
    }

    public record ParcelResponse(Long id, String code, StopSummary fromStop, StopSummary toStop,
                                 BigDecimal price, Long senderId, Long receiverId,
                                 ParcelStatus status, String deliveryOTP) implements Serializable {
    }

    public record ParcelFullResponse(Long id, String code, StopSummary fromStop, StopSummary toStop,
                                     BigDecimal price, UserSummary sender, UserSummary receiver,
                                     ParcelStatus status, String deliveryOTP) implements Serializable {
    }
}
