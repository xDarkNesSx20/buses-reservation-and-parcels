package co.edu.unimagdalena.busesreservationandparcels.services.mappers;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.TicketDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.entities.Ticket;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {TripMapper.class, StopMapper.class, UserMapper.class})
public interface TicketMapper {
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "paymentMethod", source = "paymentMethod")
    @Mapping(target = "seatNumber", source = "seatNumber")
    Ticket toEntity(TicketCreateRequest request);

    @BeanMapping(ignoreByDefault = true, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "paymentMethod", source = "paymentMethod")
    @Mapping(target = "seatNumber", source = "seatNumber")
    @Mapping(target = "status", source = "status")
    void patch(TicketUpdateRequest request, @MappingTarget Ticket ticket);

    @Mappings({
        @Mapping(target = "tripId", source = "trip.id"),
        @Mapping(target = "toStopId", source = "toStop.id"),
        @Mapping(target = "passengerId", source = "passenger.id"),
        @Mapping(target = "fromStopId", source = "fromStop.id")
    })
    TicketBasicResponse toBasicResponse(Ticket ticket);

    @Mapping(target = "passengerId", source = "passenger.id")
    TicketWithTripResponse toWithTripResponse(Ticket ticket);

    @Mapping(target = "tripId", source = "trip.id")
    TicketWithPassengerResponse toWithPassengerResponse(Ticket ticket);

    @Mapping(target = "tripId", source = "trip.id")
    @Mapping(target = "passengerId", source = "passenger.id")
    TicketResponse toResponse(Ticket ticket);

    TicketFullResponse toFullResponse(Ticket ticket);
}
