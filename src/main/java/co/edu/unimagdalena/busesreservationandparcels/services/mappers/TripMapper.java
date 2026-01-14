package co.edu.unimagdalena.busesreservationandparcels.services.mappers;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.CommonResponses.TripSummary;
import co.edu.unimagdalena.busesreservationandparcels.api.dto.TripDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.entities.Trip;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {BusMapper.class, RouteMapper.class})
public interface TripMapper {
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "date", source = "date")
    @Mapping(target = "departureAt", source = "departureAt")
    Trip toEntity(TripCreateRequest request);

    @BeanMapping(ignoreByDefault = true, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "date", source = "date")
    @Mapping(target = "departureAt", source = "departureAt")
    @Mapping(target = "status", source = "status")
    void patch(TripUpdateRequest request, @MappingTarget Trip entity);

    @Mapping(target = "routeId", source = "route.id")
    TripResponse toResponse(Trip entity);

    TripFullResponse toFullResponse(Trip entity);

    @Mapping(target = "busId", source = "bus.id")
    TripSummary toSummary(Trip entity);
}
