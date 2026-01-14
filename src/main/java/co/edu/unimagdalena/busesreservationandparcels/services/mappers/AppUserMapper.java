package co.edu.unimagdalena.busesreservationandparcels.services.mappers;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.AppUserDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.entities.AppUser;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AppUserMapper {
    @BeanMapping(ignoreByDefault = true)
    @Mappings({
        @Mapping(target = "fullName", source = "fullName"),
        @Mapping(target = "phone", source = "phone"),
        @Mapping(target = "email", source = "email"),
        @Mapping(target = "role", source = "role")
    })
    AppUser toEntity(AppUserCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, ignoreByDefault = true)
    @Mapping(target = "fullName", source = "fullName")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "email", source = "email")
    void patch(AppUserUpdateRequest request, @MappingTarget AppUser entity);

    AppUserResponse toResponse(AppUser entity);
}
