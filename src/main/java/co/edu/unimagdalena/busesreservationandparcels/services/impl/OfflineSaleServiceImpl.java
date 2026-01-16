package co.edu.unimagdalena.busesreservationandparcels.services.impl;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.OfflineSaleDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.PaymentMethod;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.SyncStatus;
import co.edu.unimagdalena.busesreservationandparcels.services.OfflineSaleService;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

public class OfflineSaleServiceImpl implements OfflineSaleService {
    @Override
    public OfflineSaleResponse create(Long tripId, OfflineSaleCreateRequest request) {
        return null;
    }

    @Override
    public List<OfflineSaleResponse> createAll(Long tripId, Set<OfflineSaleCreateRequest> requests) {
        return List.of();
    }

    @Override
    public OfflineSaleResponse get(Long id) {
        return null;
    }

    @Override
    public OfflineSaleResponse update(Long id, OfflineSaleUpdateRequest request) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public OfflineSaleResponse getByTripAndSeat(Long tripId, String seatNumber) {
        return null;
    }

    @Override
    public List<OfflineSaleResponse> getByTrip(Long tripId) {
        return List.of();
    }

    @Override
    public List<OfflineSaleResponse> getByStatus(SyncStatus status) {
        return List.of();
    }

    @Override
    public List<OfflineSaleResponse> getByPaymentMethod(PaymentMethod paymentMethod) {
        return List.of();
    }

    @Override
    public List<OfflineSaleResponse> getByCreatedAt(OffsetDateTime from, OffsetDateTime to) {
        return List.of();
    }

    @Override
    public List<OfflineSaleResponse> getBySyncedAt(OffsetDateTime from, OffsetDateTime to) {
        return List.of();
    }

    @Override
    public List<OfflineSaleResponse> getByPassenger(String passengerIdNumber) {
        return List.of();
    }

    @Override
    public List<OfflineSaleResponse> getByFromStop(Long fromStopId) {
        return List.of();
    }

    @Override
    public List<OfflineSaleResponse> getByToStop(Long toStopId) {
        return List.of();
    }

    @Override
    public List<OfflineSaleResponse> getByStops(Long fromStopId, Long toStopId) {
        return List.of();
    }

    @Override
    public void syncPending() {

    }
}
