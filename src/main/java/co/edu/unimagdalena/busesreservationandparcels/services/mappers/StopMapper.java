package co.edu.unimagdalena.busesreservationandparcels.services.mappers;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.CommonResponses.StopSummary;
import co.edu.unimagdalena.busesreservationandparcels.api.dto.StopDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.entities.Stop;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = RouteMapper.class)
public interface StopMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "route", ignore = true)
    @Mapping(target = "id", ignore = true)
    Stop toEntity(StopCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "route", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "stopOrder", ignore = true)
    void patch(StopUpdateRequest request, @MappingTarget Stop entity);

    @Mapping(target = "routeId", source = "route.id")
    StopResponse toResponse(Stop entity);

    StopFullResponse toFullResponse(Stop entity);

    StopSummary toSummary(Stop entity);
}
