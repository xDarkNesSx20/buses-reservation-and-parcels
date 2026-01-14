package co.edu.unimagdalena.busesreservationandparcels.services.mappers;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.BusDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.api.dto.CommonResponses.BusSummary;
import co.edu.unimagdalena.busesreservationandparcels.domain.entities.Bus;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {AmenityMapper.class})
public interface BusMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "amenities", ignore = true)
    Bus toEntity(BusCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "plate", ignore = true)
    @Mapping(target = "amenities", ignore = true)
    void patch(BusUpdateRequest request, @MappingTarget Bus entity);

    BusResponse toResponse(Bus entity);

    BusSummary toSummary(Bus entity);
}
