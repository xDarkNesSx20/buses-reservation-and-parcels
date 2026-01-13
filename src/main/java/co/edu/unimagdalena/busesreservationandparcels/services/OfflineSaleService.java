package co.edu.unimagdalena.busesreservationandparcels.services;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.OfflineSaleDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.PaymentMethod;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.SyncStatus;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

public interface OfflineSaleService {
    OfflineSaleResponse create(Long tripId, OfflineSaleCreateRequest request);

    List<OfflineSaleResponse> createAll(Long tripId, Set<OfflineSaleCreateRequest> requests);

    OfflineSaleResponse get(Long id);

    OfflineSaleResponse update(Long id, OfflineSaleUpdateRequest request);

    void delete(Long id);

    OfflineSaleResponse getByTripAndSeat(Long tripId, String seatNumber);

    List<OfflineSaleResponse> getByTrip(Long tripId);

    List<OfflineSaleResponse> getByStatus(SyncStatus status);

    List<OfflineSaleResponse> getByPaymentMethod(PaymentMethod paymentMethod);

    List<OfflineSaleResponse> getByCreatedAt(OffsetDateTime from, OffsetDateTime to);

    List<OfflineSaleResponse> getBySyncedAt(OffsetDateTime from, OffsetDateTime to);

    List<OfflineSaleResponse> getByPassenger(String passengerIdNumber);

    List<OfflineSaleResponse> getByFromStop(Long fromStopId);

    List<OfflineSaleResponse> getByToStop(Long toStopId);

    List<OfflineSaleResponse> getByStops(Long fromStopId, Long toStopId);

    void syncPending();
}
