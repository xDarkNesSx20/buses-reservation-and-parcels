package co.edu.unimagdalena.busesreservationandparcels.services.mappers;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.CommonResponses.RouteSummary;
import co.edu.unimagdalena.busesreservationandparcels.api.dto.RouteDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.entities.Route;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RouteMapper {
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "id", ignore = true)
    Route toEntity(RouteCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "origin", ignore = true)
    void patch(RouteUpdateRequest request, @MappingTarget Route entity);

    RouteResponse toResponse(Route entity);

    RouteSummary toSummary(Route entity);
}
