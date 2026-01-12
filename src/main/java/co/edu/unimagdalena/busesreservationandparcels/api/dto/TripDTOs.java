package co.edu.unimagdalena.busesreservationandparcels.api.dto;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.CommonResponses.RouteSummary;
import co.edu.unimagdalena.busesreservationandparcels.api.dto.CommonResponses.BusSummary;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.TripStatus;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;

public class TripDTOs {
    public record TripCreateRequest(Long busId, @NotNull LocalDate date,
                                    @NotNull OffsetDateTime departureAt) implements Serializable {
    }

    public record TripUpdateRequest(Long busId, LocalDate date, OffsetDateTime departureAt,
                                    TripStatus status) implements Serializable {
    }

    public record TripResponse(Long id, Long routeId, BusSummary bus, LocalDate date, OffsetDateTime departureAt,
                               OffsetDateTime arriveEta, TripStatus status) implements Serializable {
    }

    public record TripFullResponse(Long id, RouteSummary route, BusSummary bus, LocalDate date,
                                   OffsetDateTime departureAt,
                                   OffsetDateTime arriveEta, TripStatus status) implements Serializable {
    }
}
