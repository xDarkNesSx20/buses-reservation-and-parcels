package co.edu.unimagdalena.busesreservationandparcels.services.mappers;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.ConfigDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.entities.Config;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ConfigMapper {
    Config toEntity(ConfigCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(ConfigUpdateRequest request, @MappingTarget Config entity);

    ConfigResponse toResponse(Config entity);
}
