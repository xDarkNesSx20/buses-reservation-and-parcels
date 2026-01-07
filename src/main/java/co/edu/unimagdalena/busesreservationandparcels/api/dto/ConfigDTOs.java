package co.edu.unimagdalena.busesreservationandparcels.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.io.Serializable;
import java.math.BigDecimal;

public class ConfigDTOs {
    public record ConfigCreateRequest(@NotBlank String key, @NotNull @PositiveOrZero BigDecimal value) implements Serializable {}
    public record ConfigUpdateRequest(@NotBlank String key, @PositiveOrZero BigDecimal value) implements Serializable {}
    public record ConfigResponse(String key, BigDecimal value) implements Serializable {}
}
