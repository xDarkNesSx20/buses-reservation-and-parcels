package co.edu.unimagdalena.busesreservationandparcels.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

public class CommonResponses {
    public record TripSummary(Long id, Long busId, LocalDate date, OffsetDateTime departureAt) implements Serializable {
    }

    public record StopSummary(Long id, String name) implements Serializable {
    }

    public record RouteSummary(Long id, String code, String origin, String destination) implements Serializable {
    }

    public record BusSummary(Long id, String plate, String number) implements Serializable {
    }

    public record UserSummary(Long id, String fullName, String email, String idNumber) implements Serializable {
    }

    public record FareRuleSummary(Long id, StopSummary fromStop, StopSummary toStop,
                                  BigDecimal basePrice) implements Serializable {
    }
}
