package co.edu.unimagdalena.busesreservationandparcels.services.mappers;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.AssignmentDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.entities.Assignment;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {UserMapper.class, TripMapper.class})
public interface AssignmentMapper {
    //There's no toEntity mapping because all the properties would be ignored

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "checkListOk", source = "checkListOk")
    void patch(AssignmentUpdateRequest request, @MappingTarget Assignment entity);

    @Mapping(target = "tripId", source = "trip.id")
    AssignmentResponse toResponse(Assignment entity);

    AssignmentFullResponse toFullResponse(Assignment entity);
}
