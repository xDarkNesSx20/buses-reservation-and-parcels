package co.edu.unimagdalena.busesreservationandparcels.services.impl;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.TripDTOs.TripCreateRequest;
import co.edu.unimagdalena.busesreservationandparcels.api.dto.TripDTOs.TripFullResponse;
import co.edu.unimagdalena.busesreservationandparcels.api.dto.TripDTOs.TripResponse;
import co.edu.unimagdalena.busesreservationandparcels.api.dto.TripDTOs.TripUpdateRequest;
import co.edu.unimagdalena.busesreservationandparcels.domain.entities.Trip;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.BusStatus;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.TripStatus;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.BusRepository;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.RouteRepository;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.TripRepository;
import co.edu.unimagdalena.busesreservationandparcels.exceptions.NotFoundException;
import co.edu.unimagdalena.busesreservationandparcels.services.TripService;
import co.edu.unimagdalena.busesreservationandparcels.services.mappers.TripMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {
    private final TripRepository tripRepo;
    private final TripMapper mapper;
    private final RouteRepository routeRepo;
    private final BusRepository busRepo;

    @Override
    @Transactional
    public TripFullResponse create(Long routeId, TripCreateRequest request) {
        var route = routeRepo.findById(routeId).orElseThrow(
                () -> new NotFoundException("Route with id %d not found.".formatted(routeId))
        );
        var trip = mapper.toEntity(request);
        trip.setRoute(route);

        setBusToTrip(trip, request.busId());
        trip.setArrivalEta(OffsetDateTime.now().plusMinutes(route.getDurationMin()));

        return mapper.toFullResponse(tripRepo.save(trip));
    }

    @Override
    public TripResponse get(Long id) {
        var trip = tripRepo.findById(id).orElseThrow(
                () -> new NotFoundException("Trip with id %d not found.".formatted(id))
        );
        return mapper.toResponse(trip);
    }

    @Override
    @Transactional
    public TripResponse update(Long id, TripUpdateRequest request) {
        var trip = tripRepo.findById(id).orElseThrow(
                () -> new NotFoundException("Trip with id %d not found.".formatted(id))
        );
        mapper.patch(request, trip);

        if (request.departureAt() != null)
            trip.setDate(LocalDate.from(request.departureAt()));

        trip = setBusToTrip(trip, request.busId());

        return mapper.toResponse(tripRepo.save(trip));
    }

    private Trip setBusToTrip(Trip trip, Long busId) {
        if (busId != null){
            var bus = busRepo.findById(busId).orElseThrow(
                    () -> new NotFoundException("Bus with id %d not found.".formatted(busId))
            );
            if (!bus.getStatus().equals(BusStatus.AVAILABLE))
                throw new IllegalArgumentException("Bus with id %d is not available.".formatted(busId));
            trip.setBus(bus);
        }
        return trip;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        tripRepo.deleteById(id);
    }

    @Override
    public TripFullResponse getWithDetails(Long id) {
        var trip = tripRepo.findByIdWithDetails(id).orElseThrow(
                () -> new NotFoundException("Trip with id %d not found.".formatted(id))
        );
        return mapper.toFullResponse(trip);
    }

    @Override
    public List<TripResponse> getByRoute(Long routeId) {
        if (routeRepo.existsById(routeId))
            throw new NotFoundException("Route with id %d not found.".formatted(routeId));

        return tripRepo.findByRoute_Id(routeId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<TripResponse> getByStatus(TripStatus status) {
        return tripRepo.findByStatus(status).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<TripResponse> getByDate(LocalDate date) {
        return tripRepo.findByDate(date).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public Page<TripResponse> getByDate(LocalDate from, LocalDate to, Pageable pageable) {
        if (from.isAfter(to))
            throw new IllegalArgumentException("From date must be before to date.");
        return tripRepo.findByDateBetween(from, to, pageable)
                .map(mapper::toResponse);
    }

    @Override
    public List<TripResponse> getByDepartureAt(OffsetDateTime from, OffsetDateTime to) {
        if (from.isAfter(to))
            throw new IllegalArgumentException("From date must be before to date.");
        return tripRepo.findByDepartureAtBetween(from, to).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<TripResponse> getByRouteAndStatus(Long routeId, TripStatus status) {
        if (routeRepo.existsById(routeId))
            throw new NotFoundException("Route with id %d not found.".formatted(routeId));

        return tripRepo.findByRoute_IdAndStatus(routeId, status).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<TripResponse> getByRouteAndDate(Long routeId, LocalDate date) {
        if (routeRepo.existsById(routeId))
            throw new NotFoundException("Route with id %d not found.".formatted(routeId));

        return tripRepo.findByRoute_IdAndDate(routeId, date).stream()
                .map(mapper::toResponse)
                .toList();
    }
}
