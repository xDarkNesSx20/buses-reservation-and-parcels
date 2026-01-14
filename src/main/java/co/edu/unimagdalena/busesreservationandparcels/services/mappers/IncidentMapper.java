package co.edu.unimagdalena.busesreservationandparcels.services.mappers;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.IncidentDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.entities.Incident;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface IncidentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Incident toEntity(IncidentCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, ignoreByDefault = true)
    @Mapping(target = "entityId", source = "entityId")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "note", source = "note")
    void patch(IncidentUpdateRequest request, @MappingTarget Incident entity);

    IncidentResponse toResponse(Incident entity);
}
