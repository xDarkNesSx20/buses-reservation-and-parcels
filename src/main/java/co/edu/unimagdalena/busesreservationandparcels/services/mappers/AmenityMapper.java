package co.edu.unimagdalena.busesreservationandparcels.services.mappers;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.AmenityDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.entities.Amenity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface AmenityMapper {
    @Mapping(target = "id", ignore = true)
    Amenity toEntity(AmenityCreateRequest request);

    @Mapping(target = "id", ignore = true)
    void patch(AmenityUpdateRequest request, @MappingTarget Amenity entity);

    AmenityResponse toResponse(Amenity amenity);

    Set<AmenityResponse> toSetResponse(Set<Amenity> amenities);
}
