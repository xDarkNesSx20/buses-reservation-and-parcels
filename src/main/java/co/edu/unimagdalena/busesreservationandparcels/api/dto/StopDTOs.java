package co.edu.unimagdalena.busesreservationandparcels.api.dto;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.CommonResponses.RouteSummary;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.io.Serializable;
import java.math.BigDecimal;

public class StopDTOs {
    public record StopCreateRequest(@NotBlank String name, @PositiveOrZero Integer stopOrder, BigDecimal latitude,
                                    BigDecimal longitude) implements Serializable {
    }

    public record StopUpdateRequest(String name, Integer stopOrder) implements Serializable {
    }

    public record StopResponse(Long id, Long routeId, String name, Integer stopOrder, BigDecimal latitude,
                               BigDecimal longitude) implements Serializable {
    }

    public record StopFullResponse(Long id, RouteSummary route, String name, Integer stopOrder, BigDecimal latitude,
                                   BigDecimal longitude) implements Serializable {
    }
}
