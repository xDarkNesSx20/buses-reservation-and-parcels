package co.edu.unimagdalena.busesreservationandparcels.services;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.TripDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.TripStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

public interface TripService {
    TripFullResponse create(Long routeId, TripCreateRequest request);
    TripResponse get(Long id);
    TripResponse update(Long id, TripUpdateRequest request);
    void delete(Long id);

    TripFullResponse getWithDetails(Long id);
    List<TripResponse> getByRoute(Long routeId);
    List<TripResponse> getByStatus(TripStatus status);
    List<TripResponse> getByDate(LocalDate date);
    Page<TripResponse> getByDate(LocalDate from, LocalDate to, Pageable pageable);
    List<TripResponse> getByDepartureAt(OffsetDateTime from, OffsetDateTime to);
    List<TripResponse> getByRouteAndStatus(Long routeId, TripStatus status);
    List<TripResponse> getByRouteAndDate(Long routeId, LocalDate date);
}
