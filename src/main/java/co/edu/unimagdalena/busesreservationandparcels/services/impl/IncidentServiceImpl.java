package co.edu.unimagdalena.busesreservationandparcels.services.impl;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.IncidentDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.EntityType;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.IncidentType;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.IncidentRepository;
import co.edu.unimagdalena.busesreservationandparcels.exceptions.NotFoundException;
import co.edu.unimagdalena.busesreservationandparcels.services.IncidentService;
import co.edu.unimagdalena.busesreservationandparcels.services.mappers.IncidentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncidentServiceImpl implements IncidentService {
    private final IncidentRepository incidentRepo;
    private final IncidentMapper mapper;

    @Override
    @Transactional
    public IncidentResponse create(IncidentCreateRequest request) {
        var incident = mapper.toEntity(request);
        return mapper.toResponse(incidentRepo.save(incident));
    }

    @Override
    public IncidentResponse get(Long id) {
        var incident = incidentRepo.findById(id).orElseThrow(
                () -> new NotFoundException("Incident with id %d not found.".formatted(id))
        );
        return mapper.toResponse(incident);
    }

    @Override
    @Transactional
    public IncidentResponse update(Long id, IncidentUpdateRequest request) {
        var incident = incidentRepo.findById(id).orElseThrow(
                () -> new NotFoundException("Incident with id %d not found.".formatted(id))
        );
        mapper.patch(request, incident);
        return mapper.toResponse(incidentRepo.save(incident));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        incidentRepo.deleteById(id);
    }

    @Override
    public List<IncidentResponse> getAll() {
        return incidentRepo.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<IncidentResponse> getByType(IncidentType type) {
        return incidentRepo.findByType(type).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<IncidentResponse> getByEntityType(EntityType entityType) {
        return incidentRepo.findByEntityType(entityType).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<IncidentResponse> getByTypeAndEntityType(IncidentType type, EntityType entityType) {
        return incidentRepo.findByTypeAndEntityType(type, entityType).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<IncidentResponse> getByCreatedAt(OffsetDateTime start, OffsetDateTime end) {
        if (end.isBefore(start)) throw new IllegalArgumentException("End date must be after start date.");
        return incidentRepo.findByCreatedAtBetween(start, end).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<IncidentResponse> getByEntity(Long entityId, EntityType entityType) {
        return incidentRepo.findByEntityIdAndEntityType(entityId, entityType).stream()
                .map(mapper::toResponse)
                .toList();
    }
}
