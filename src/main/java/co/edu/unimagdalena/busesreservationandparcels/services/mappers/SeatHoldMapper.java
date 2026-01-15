package co.edu.unimagdalena.busesreservationandparcels.services.mappers;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.SeatHoldDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.entities.SeatHold;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {TripMapper.class, UserMapper.class})
public interface SeatHoldMapper {
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "seatNumber", source = "seatNumber")
    SeatHold toEntity(SeatHoldCreateRequest request);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "seatNumber", source = "seatNumber")
    @Mapping(target = "status", source = "status")
    void patch(SeatHoldUpdateRequest request, @MappingTarget SeatHold entity);

    @Mapping(target = "tripId", source = "trip.id")
    @Mapping(target = "passengerId", source = "passenger.id")
    SeatHoldResponse toResponse(SeatHold entity);

    @Mapping(target = "passengerId", source = "passenger.id")
    SeatHoldWTResponse toWTResponse(SeatHold entity);

    @Mapping(target = "tripId", source = "trip.id")
    SeatHoldWPResponse toWPResponse(SeatHold entity);

    SeatHoldFullResponse toFullResponse(SeatHold entity);
}
