package co.edu.unimagdalena.busesreservationandparcels.services.impl;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.SeatHoldDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.entities.AppUser;
import co.edu.unimagdalena.busesreservationandparcels.domain.entities.SeatHold;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.SeatHoldStatus;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.AppUserRepository;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.SeatHoldRepository;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.TripRepository;
import co.edu.unimagdalena.busesreservationandparcels.exceptions.AlreadyExistsException;
import co.edu.unimagdalena.busesreservationandparcels.exceptions.NotFoundException;
import co.edu.unimagdalena.busesreservationandparcels.services.SeatHoldService;
import co.edu.unimagdalena.busesreservationandparcels.services.mappers.SeatHoldMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatHoldServiceImpl implements SeatHoldService {
    private final SeatHoldRepository seatHoldRepo;
    private final SeatHoldMapper mapper;
    private final TripRepository tripRepo;
    private final AppUserRepository appUserRepo;

    @Override
    @Transactional
    public SeatHoldFullResponse create(Long tripId, SeatHoldCreateRequest request) {
        var trip = tripRepo.findById(tripId).orElseThrow(
                () -> new IllegalArgumentException("Trip with id %d not found.".formatted(tripId))
        );

        if (seatHoldRepo.isBooked(tripId, request.seatNumber()))
            throw new AlreadyExistsException("Seat %s is already booked for trip with id %d."
                    .formatted(request.seatNumber(), tripId));

        var passenger = appUserRepo.findById(request.passengerId()).orElseThrow(
                () -> new IllegalArgumentException("AppUser with id %d not found.".formatted(request.passengerId()))
        );
        if (!passenger.getActive())
            throw new IllegalArgumentException("AppUser with id %d is not active.".formatted(request.passengerId()));

        var seatHold = mapper.toEntity(request);
        seatHold.setTrip(trip);
        seatHold.setPassenger(passenger);
        //TODO: Do what I wrote in ConfigServiceImpl to set the expiration time
        //seatHold.setExpiresAt(OffsetDateTime.now().plusMinutes(10)); //
        return mapper.toFullResponse(seatHoldRepo.save(seatHold));
    }

    @Override
    @Transactional
    public List<SeatHoldResponse> createAll(Long tripId, Set<SeatHoldCreateRequest> requests) {
        var trip = tripRepo.findById(tripId).orElseThrow(
                () -> new IllegalArgumentException("Trip with id %d not found.".formatted(tripId))
        );
        var passengers = bringPassengers(requests);
        List<SeatHold> seatHolds = requests.stream()
                .map(req -> {
                    if (seatHoldRepo.isBooked(tripId, req.seatNumber()))
                        throw new AlreadyExistsException("Seat %s is already booked for trip with id %d."
                                .formatted(req.seatNumber(), tripId));

                    var seatHold = mapper.toEntity(req);
                    seatHold.setTrip(trip);

                    var passenger = passengers.stream()
                            .filter(p -> p.getId().equals(req.passengerId()))
                            .findFirst()
                            .orElseThrow();
                    seatHold.setPassenger(passenger);

                    return seatHold;
                }).toList();

        return seatHoldRepo.saveAll(seatHolds).stream()
                .map(mapper::toResponse)
                .toList();
    }

    private List<AppUser> bringPassengers(Set<SeatHoldCreateRequest> requests) {
        var passengerIds = requests.stream()
                .map(SeatHoldCreateRequest::passengerId)
                .collect(Collectors.toSet());
        var passengers = appUserRepo.findAllById(passengerIds);
        if (passengers.size() != passengerIds.size()) {
            var foundIds = passengers.stream().map(AppUser::getId)
                    .collect(Collectors.toSet());
            passengerIds.removeAll(foundIds);
            throw new IllegalArgumentException("AppUsers not found: " + passengerIds);
        }
        passengers.forEach(p -> {
            if (!p.getActive())
                throw new IllegalArgumentException("AppUser with id %d is not active.".formatted(p.getId()));
        });

        return passengers;
    }

    @Override
    public SeatHoldResponse get(Long id) {
        var seatHold = seatHoldRepo.findById(id).orElseThrow(
                () -> new NotFoundException("SeatHold with id %d not found.".formatted(id))
        );
        return mapper.toResponse(seatHold);
    }

    @Override
    @Transactional
    public SeatHoldResponse update(Long id, SeatHoldUpdateRequest request) {
        var seatHold = seatHoldRepo.findById(id).orElseThrow(
                () -> new NotFoundException("SeatHold with id %d not found.".formatted(id))
        );
        mapper.patch(request, seatHold);
        return mapper.toResponse(seatHoldRepo.save(seatHold));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        seatHoldRepo.deleteById(id);
    }

    @Override
    public List<SeatHoldResponse> getByTrip(Long tripId) {
        if (!tripRepo.existsById(tripId))
            throw new NotFoundException("Trip with id %d not found.".formatted(tripId));

        return seatHoldRepo.findByTrip_Id(tripId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<SeatHoldWPResponse> getByTripWithDetails(Long tripId) {
        if (!tripRepo.existsById(tripId))
            throw new NotFoundException("Trip with id %d not found.".formatted(tripId));

        return seatHoldRepo.findByTrip_IdWithPassenger(tripId).stream()
                .map(mapper::toWPResponse)
                .toList();
    }

    @Override
    public List<SeatHoldWTResponse> getByPassengerWithDetails(Long passengerId) {
        if (!appUserRepo.existsById(passengerId))
            throw new NotFoundException("AppUser with id %d not found.".formatted(passengerId));

        return seatHoldRepo.findByPassenger_IdWithTrip(passengerId).stream()
                .map(mapper::toWTResponse)
                .toList();
    }

    @Override
    public List<SeatHoldResponse> getByStatus(SeatHoldStatus status) {
        return seatHoldRepo.findByStatus(status).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<SeatHoldResponse> getByTripAndStatus(Long tripId, SeatHoldStatus status) {
        if (!tripRepo.existsById(tripId))
            throw new NotFoundException("Trip with id %d not found.".formatted(tripId));

        return seatHoldRepo.findByTrip_IdAndStatus(tripId, status).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<SeatHoldResponse> getByPassenger(Long passengerId) {
        if (!appUserRepo.existsById(passengerId))
            throw new NotFoundException("AppUser with id %d not found.".formatted(passengerId));

        return seatHoldRepo.findByPassenger_Id(passengerId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public SeatHoldFullResponse getWithDetails(Long id) {
        var seatHold = seatHoldRepo.findByIdWithDetails(id).orElseThrow(
                () -> new NotFoundException("SeatHold with id %d not found.".formatted(id))
        );
        return mapper.toFullResponse(seatHold);
    }

    @Scheduled(fixedRate = 60000, initialDelay = 30000)
    @Override
    @Transactional
    public void setHoldsToExpired() {
        var now = OffsetDateTime.now();
        var seatsHold = seatHoldRepo.findNearToExpiration(now);

        if (seatsHold.isEmpty()) return;

        seatsHold.forEach(sh -> sh.setStatus(SeatHoldStatus.EXPIRED));
        seatHoldRepo.saveAll(seatsHold);
    }
}
