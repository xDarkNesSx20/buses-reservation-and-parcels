package co.edu.unimagdalena.busesreservationandparcels.services.mappers;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.OfflineSaleDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.entities.OfflineSale;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OfflineSaleMapper {
    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "createdAt", ignore = true), //has default value
        @Mapping(target = "status", ignore = true), //it too
        @Mapping(target = "syncAttempts", ignore = true), //it too
        @Mapping(target = "syncedAt", ignore = true),
        @Mapping(target = "ticketId", ignore = true),
        @Mapping(target = "tripId", ignore = true)
    })
    OfflineSale toEntity(OfflineSaleCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, ignoreByDefault = true)
    @Mappings({
            @Mapping(target = "seatNumber", source = "seatNumber"),
            @Mapping(target = "passengerIdNumber", source = "passengerIdNumber"),
            @Mapping(target = "passengerPhone", source = "passengerPhone"),
            @Mapping(target = "price", source = "price"),
            @Mapping(target = "paymentMethod", source = "paymentMethod")
    })
    void patch(OfflineSaleUpdateRequest request, @MappingTarget OfflineSale entity);

    OfflineSaleResponse toResponse(OfflineSale entity);
}
