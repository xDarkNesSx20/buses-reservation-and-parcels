package co.edu.unimagdalena.busesreservationandparcels.domain.repositories;

import co.edu.unimagdalena.busesreservationandparcels.domain.entities.Incident;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.EntityType;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.IncidentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface IncidentRepository extends JpaRepository<Incident, Long> {
    List<Incident> findByType(IncidentType type);
    List<Incident> findByEntityType(EntityType entityType);
    List<Incident> findByTypeAndEntityType(IncidentType type, EntityType entityType);
    Optional<Incident> findByEntityId(Long entityId);
    List<Incident> findByCreatedAtBetween(OffsetDateTime from, OffsetDateTime to);
}
