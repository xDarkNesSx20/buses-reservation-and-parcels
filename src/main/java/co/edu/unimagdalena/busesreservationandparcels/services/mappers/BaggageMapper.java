package co.edu.unimagdalena.busesreservationandparcels.services.mappers;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.BaggageDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.entities.Baggage;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BaggageMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ticket", ignore = true)
    @Mapping(target = "tagCode", ignore = true)
    Baggage toEntity(BaggageCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ticket", ignore = true)
    @Mapping(target = "tagCode", ignore = true)
    void patch(BaggageUpdateRequest request, @MappingTarget Baggage entity);

    @Mapping(target = "ticketId", source = "ticket.id")
    BaggageResponse toResponse(Baggage entity);
}
