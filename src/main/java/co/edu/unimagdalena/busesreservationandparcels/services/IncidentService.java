package co.edu.unimagdalena.busesreservationandparcels.services;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.IncidentDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.EntityType;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.IncidentType;

import java.time.OffsetDateTime;
import java.util.List;

public interface IncidentService {
    IncidentResponse create(IncidentCreateRequest request);

    IncidentResponse get(Long id);

    IncidentResponse update(Long id, IncidentUpdateRequest request);

    void delete(Long id);

    List<IncidentResponse> getAll();

    List<IncidentResponse> getByType(IncidentType type);

    List<IncidentResponse> getByEntityType(EntityType entityType);

    List<IncidentResponse> getByTypeAndEntityType(IncidentType type, EntityType entityType);

    List<IncidentResponse> getByCreatedAt(OffsetDateTime start, OffsetDateTime end);

    IncidentResponse getByEntity(Long entityId, EntityType entityType);
}
