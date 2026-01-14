package co.edu.unimagdalena.busesreservationandparcels.services.mappers;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.OfflineUserDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.entities.OfflineUser;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OfflineUserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    OfflineUser toEntity(OfflineUserCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, ignoreByDefault = true)
    @Mapping(target = "fullName", source = "fullName")
    @Mapping(target = "phone", source = "phone")
    void patch(OfflineUserUpdateRequest request, @MappingTarget OfflineUser entity);

    OfflineUserResponse toResponse(OfflineUser entity);
}
