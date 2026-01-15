package co.edu.unimagdalena.busesreservationandparcels.services.mappers;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.SeatDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.entities.Seat;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = BusMapper.class)
public interface SeatMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bus", ignore = true)
    Seat toEntity(SeatCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bus", ignore = true)
    void patch(SeatUpdateRequest request, @MappingTarget Seat seat);

    @Mapping(target = "busId", source = "bus.id")
    SeatResponse toResponse(Seat seat);

    SeatFullResponse toFullResponse(Seat seat);
}
