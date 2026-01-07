package co.edu.unimagdalena.busesreservationandparcels.api.dto;

import co.edu.unimagdalena.busesreservationandparcels.domain.enums.DiscountType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.io.Serializable;
import java.math.BigDecimal;

public class DiscountDTOs {
    public record DiscountCreateRequest(@NotNull DiscountType type,
                                        @NotNull @PositiveOrZero BigDecimal value) implements Serializable {
    }

    public record DiscountUpdateRequest(@NotNull DiscountType type,
                                        @PositiveOrZero BigDecimal value) implements Serializable {
    }

    public record DiscountResponse(Long id, Long fareRuleId, DiscountType type,
                                   BigDecimal value) implements Serializable {
    }

    public record DiscountFullResponse(Long id, /*FareRuleSummary fareRule,*/ DiscountType type,
                                       BigDecimal value) implements Serializable {
    }
}
