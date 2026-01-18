package co.edu.unimagdalena.busesreservationandparcels.services.impl;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.TicketDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.TicketStatus;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.*;
import co.edu.unimagdalena.busesreservationandparcels.exceptions.AlreadyExistsException;
import co.edu.unimagdalena.busesreservationandparcels.exceptions.CannotCompleteRequestException;
import co.edu.unimagdalena.busesreservationandparcels.exceptions.NotFoundException;
import co.edu.unimagdalena.busesreservationandparcels.services.OfflineUserService;
import co.edu.unimagdalena.busesreservationandparcels.services.TicketService;
import co.edu.unimagdalena.busesreservationandparcels.services.mappers.TicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepo;
    private final TicketMapper mapper;
    private final TripRepository tripRepo;
    private final AppUserRepository appUserRepo;
    private final OfflineUserRepository offUserRepo;
    private final StopRepository stopRepo;
    private final OfflineUserService offUserService;
    private final FareRuleRepository fareRuleRepo;

    @Override
    public TicketFullResponse createInApp(Long tripId, TicketAppCreateRequest request) {
        var trip = tripRepo.findById(tripId).orElseThrow(
                () -> new NotFoundException("Trip with id %d not found.".formatted(tripId))
        );

        //0: No overbooking; -1: One ticket for overbooking
        if (tripRepo.countFreeSeats(tripId) <= -1)
            throw new CannotCompleteRequestException(
                    "There's no available seats in the trip with id %d".formatted(tripId));

        var fromStop = stopRepo.findById(request.fromStopId()).orElseThrow(
                () -> new NotFoundException("Stop with id %d not found.".formatted(request.fromStopId()))
        );

        var toStop = stopRepo.findById(request.toStopId()).orElseThrow(
                () -> new NotFoundException("Stop with id %d not found.".formatted(request.toStopId()))
        );

        if (fromStop.getStopOrder() >= toStop.getStopOrder())
            throw new IllegalArgumentException("ToStop order must be greater than FromStop order.");

        if (!fromStop.getRoute().getId().equals(toStop.getRoute().getId()))
            throw new IllegalArgumentException("FromStop and ToStop have to be in the same route.");

        if (trip.getRoute().getId().equals(fromStop.getRoute().getId()))
            throw new IllegalArgumentException("FromStop and ToStop route must match with Trip route.");

        if (!ticketRepo.isSeatAvailable(tripId, request.seatNumber(), fromStop.getStopOrder()))
            throw new AlreadyExistsException(
                    "The seat %s already has been taken for trip with id %d."
                            .formatted(request.seatNumber(), tripId));

        var passenger = appUserRepo.findById(request.passengerId()).orElseThrow(
                () -> new NotFoundException("AppUser with id %d not found.".formatted(request.passengerId()))
        );

        var ticket = mapper.toEntity(request);
        ticket.setTrip(trip);
        ticket.setFromStop(fromStop);
        ticket.setToStop(toStop);
    }

    @Override
    public List<TicketResponse> createAllInApp(Long tripId, Set<TicketAppCreateRequest> requests) {
        return List.of();
    }

    @Override
    public TicketFullResponse createInOffice(Long tripId, TicketOfficeCreateRequest request) {
        return null;
    }

    @Override
    public List<TicketResponse> createAllInOffice(Long tripId, Set<TicketOfficeCreateRequest> requests) {
        return List.of();
    }

    @Override
    public TicketResponse get(Long id) {
        return null;
    }

    @Override
    public TicketResponse update(Long id, TicketUpdateRequest request) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<TicketBasicResponse> getByTrip(Long tripId) {
        return List.of();
    }

    @Override
    public List<TicketBasicResponse> getByPassenger(Long passengerId) {
        return List.of();
    }

    @Override
    public TicketBasicResponse getByTripAndSeat(Long tripId, String seatNumber) {
        return null;
    }

    @Override
    public List<TicketBasicResponse> getByStatusAndTrip(TicketStatus status, Long tripId) {
        return List.of();
    }

    @Override
    public List<TicketBasicResponse> getByTripAndStops(Long tripId, Long fromStopId, Long toStopId) {
        return List.of();
    }

    @Override
    public TicketResponse getWithStops(Long id) {
        return null;
    }

    @Override
    public List<TicketResponse> getByTripWithStops(Long tripId) {
        return List.of();
    }

    @Override
    public List<TicketResponse> getByPassengerWithStops(Long passengerId) {
        return List.of();
    }

    @Override
    public Page<TicketResponse> getByStatusWithStops(TicketStatus status, Pageable pageable) {
        return null;
    }

    @Override
    public TicketWithTripResponse getWithTripAndStops(Long id) {
        return null;
    }

    @Override
    public List<TicketWithTripResponse> getByPassengerWithTripAndStops(Long passengerId) {
        return List.of();
    }

    @Override
    public TicketWithPassengerResponse getWithPassengerAndStops(Long id) {
        return null;
    }

    @Override
    public List<TicketWithPassengerResponse> getByTripWithPassengerAndStops(Long tripId) {
        return List.of();
    }

    @Override
    public TicketFullResponse getFullTicket(Long id) {
        return null;
    }
}
