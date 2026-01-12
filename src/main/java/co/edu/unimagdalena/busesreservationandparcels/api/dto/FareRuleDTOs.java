package co.edu.unimagdalena.busesreservationandparcels.api.dto;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.CommonResponses.StopSummary;
import co.edu.unimagdalena.busesreservationandparcels.api.dto.CommonResponses.RouteSummary;
import jakarta.validation.constraints.NotNull;
import co.edu.unimagdalena.busesreservationandparcels.api.dto.DiscountDTOs.DiscountResponse;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

public class FareRuleDTOs {
    public record FareRuleCreateRequest(@NotNull Long fromStopId, @NotNull Long toStopId,
                                        @Positive BigDecimal basePrice,
                                        Boolean dynamicPricing) implements Serializable {
    }

    public record FareRuleUpdateRequest(@Positive BigDecimal basePrice,
                                        Boolean dynamicPricing) implements Serializable {
    }

    public record FareRuleResponse(Long id, Long routeId, StopSummary fromStop, StopSummary toStop,
                                   BigDecimal basePrice,
                                   Boolean dynamicPricing, Set<DiscountResponse> discounts) implements Serializable {
    }

    public record FareRuleFullResponse(Long id, RouteSummary route, StopSummary fromStop, StopSummary toStop,
                                   BigDecimal basePrice,
                                   Boolean dynamicPricing, Set<DiscountResponse> discounts) implements Serializable {
    }
}
