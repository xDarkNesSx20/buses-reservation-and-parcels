package co.edu.unimagdalena.busesreservationandparcels.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;
import java.math.BigDecimal;

public class RouteDTOs {
    public record RouteCreateRequest(@NotBlank String name, @NotBlank String origin, @NotBlank String destination,
                                     @Positive BigDecimal distanceKm,
                                     @Positive Integer durationMin) implements Serializable {
    }

    public record RouteUpdateRequest(String name, String destination, @Positive BigDecimal distanceKm,
                                     @Positive Integer durationMin) implements Serializable {
    }

    public record RouteResponse(Long id, String code, String name, String origin, String destination,
                                BigDecimal distanceKm, Integer durationMin) implements Serializable {
    }
}
