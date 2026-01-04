package co.edu.unimagdalena.busesreservationandparcels.domain.repositories;

import co.edu.unimagdalena.busesreservationandparcels.domain.entities.SeatHold;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.SeatHoldStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SeatHoldRepository extends JpaRepository<SeatHold, Long> {
    List<SeatHold> findByTrip_Id(Long tripId);

    @Query("SELECT SH FROM SeatHold SH JOIN FETCH SH.passenger WHERE SH.trip.id = :tripId")
    List<SeatHold> findByTrip_IdWithPassenger(@Param("tripId") Long tripId);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM seats_hold WHERE trip_id = :tripId AND seat_number = :seatNumber AND status = 'HOLD')",
        nativeQuery = true)
    boolean isBooked(@Param("tripId") Long tripId, @Param("seatNumber") String seatNumber);

    List<SeatHold> findByStatus(SeatHoldStatus status);
    List<SeatHold> findByTrip_IdAndStatus(Long tripId, SeatHoldStatus status);
    List<SeatHold> findByPassenger_Id(Long passengerId);

    @Query("SELECT SH FROM SeatHold SH JOIN FETCH SH.trip WHERE SH.passenger.id = :passengerId")
    List<SeatHold> findByPassenger_IdWithTrip(@Param("passengerId") Long passengerId);

    @EntityGraph(attributePaths = {"trip", "passenger"})
    @Query(" SELECT SH FROM SeatHold SH WHERE SH.id = :id")
    Optional<SeatHold> findByIdWithDetails(@Param("id") Long id);
}
