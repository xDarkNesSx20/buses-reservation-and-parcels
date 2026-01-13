package co.edu.unimagdalena.busesreservationandparcels.services;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.SeatHoldDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.SeatHoldStatus;

import java.util.List;
import java.util.Set;

public interface SeatHoldService {
    SeatHoldFullResponse create(Long tripId, SeatHoldCreateRequest request);
    List<SeatHoldResponse> createAll(Long tripId, Set<SeatHoldCreateRequest> requests);
    SeatHoldResponse get(Long id);
    SeatHoldResponse update(Long id, SeatHoldUpdateRequest request);
    void delete(Long id);

    List<SeatHoldResponse> getByTrip(Long tripId);
    List<SeatHoldWPResponse> getByTripWithDetails(Long tripId);
    List<SeatHoldWTResponse> getByPassengerWithDetails(Long passengerId);
    List<SeatHoldResponse> getByStatus(SeatHoldStatus status);
    List<SeatHoldResponse> getByTripAndStatus(Long tripId, SeatHoldStatus status);
    List<SeatHoldResponse> getByPassenger(Long passengerId);
    SeatHoldFullResponse getWithDetails(Long id);
}
