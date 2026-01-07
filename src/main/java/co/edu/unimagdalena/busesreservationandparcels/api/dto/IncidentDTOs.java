package co.edu.unimagdalena.busesreservationandparcels.api.dto;

import co.edu.unimagdalena.busesreservationandparcels.domain.enums.EntityType;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.IncidentType;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class IncidentDTOs {
    public record IncidentCreateRequest(@NotNull EntityType entityType, @NotNull Long entityId,
                                        @NotNull IncidentType type, String note) implements Serializable {
    }

    public record IncidentUpdateRequest(Long entityId, IncidentType type, String note) implements Serializable {
    }

    public record IncidentResponse(Long id, EntityType entityType, Long entityId, IncidentType type,
                                   OffsetDateTime createdAt, String note) implements Serializable {
    }
}
