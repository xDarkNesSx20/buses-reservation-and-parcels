package co.edu.unimagdalena.busesreservationandparcels.domain.repositories;

import co.edu.unimagdalena.busesreservationandparcels.domain.entities.OfflineSale;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.PaymentMethod;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.SyncStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface OfflineSaleRepository extends JpaRepository<OfflineSale, Long> {
    List<OfflineSale> findByTripId(Long tripId);
    Optional<OfflineSale> findByTripIdAndSeatNumber(Long tripId, String seatNumber);
    List<OfflineSale> findByStatus(SyncStatus status);
    List<OfflineSale> findByPaymentMethod(PaymentMethod paymentMethod);
    List<OfflineSale> findByCreatedAtBetween(OffsetDateTime start, OffsetDateTime end);
    List<OfflineSale> findBySyncedAtBetween(OffsetDateTime start, OffsetDateTime end);
    List<OfflineSale> findByPassengerIdNumber(String passengerIdNumber);
    List<OfflineSale> findByFromStopId(Long stopId);
    List<OfflineSale> findByToStopId(Long stopId);
    List<OfflineSale> findByFromStopIdAndToStopId(Long fromStopId, Long toStopId);

    @Query("SELECT OS FROM OfflineSale OS WHERE OS.status IN ('PENDING', 'CONFLICT')")
    List<OfflineSale> findPendingToSync();

    //JUST IN CASE
    @Modifying
    @Query("DELETE FROM OfflineSale OS WHERE OS.status = 'FAILED'")
    void deleteFailedToSync();
}
