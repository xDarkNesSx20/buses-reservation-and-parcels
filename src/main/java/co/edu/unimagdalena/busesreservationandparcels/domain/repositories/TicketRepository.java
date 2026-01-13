package co.edu.unimagdalena.busesreservationandparcels.domain.repositories;

import co.edu.unimagdalena.busesreservationandparcels.domain.entities.Ticket;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.TicketStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByTripId(Long tripId);

    List<Ticket> findByPassengerId(Long passengerId);

    Optional<Ticket> findByTripIdAndSeatNumber(Long tripId, String seatNumber);

    @EntityGraph(attributePaths = {"fromStop", "toStop"})
    @Query("SELECT T FROM Ticket T WHERE T.id = :id")
    Optional<Ticket> findByIdWithStops(@Param("id") Long id);

    @EntityGraph(attributePaths = {"fromStop", "toStop"})
    @Query("SELECT T FROM Ticket T WHERE T.trip.id = :tripId")
    List<Ticket> findByTripIdWithStops(@Param("tripId") Long tripId);

    @EntityGraph(attributePaths = {"fromStop", "toStop"})
    @Query("SELECT T FROM Ticket T WHERE T.passenger.id = :passengerId")
    List<Ticket> findByPassengerIdWithStops(@Param("passengerId") Long passengerId);

    @EntityGraph(attributePaths = {"fromStop", "toStop"})
    @Query("SELECT T FROM Ticket T WHERE T.status = :status")
    Page<Ticket> findByStatusWithStops(@Param("status") TicketStatus status, Pageable pageable);

    @EntityGraph(attributePaths = {"trip", "fromStop", "toStop"})
    @Query("SELECT T FROM Ticket T WHERE T.id = :id")
    Optional<Ticket> findByIdWithTripAndStops(@Param("id") Long id);

    @EntityGraph(attributePaths = {"trip", "fromStop", "toStop"})
    @Query("SELECT T FROM Ticket T WHERE T.passenger.id = :passengerId")
    List<Ticket> findByPassengerIdWithTripAndStops(@Param("passengerId") Long passengerId);

    @EntityGraph(attributePaths = {"passenger", "fromStop", "toStop"})
    @Query("SELECT T FROM Ticket T WHERE T.id = :id")
    Optional<Ticket> findByIdWithPassengerAndStops(@Param("id") Long id);

    @EntityGraph(attributePaths = {"passenger", "fromStop", "toStop"})
    @Query("SELECT T FROM Ticket T WHERE T.trip.id = :tripId")
    List<Ticket> findByTripIdWithPassengerAndStops(@Param("tripId") Long tripId);

    @EntityGraph(attributePaths = {"trip", "passenger", "fromStop", "toStop"})
    @Query("SELECT T FROM Ticket T WHERE T.id = :id")
    Optional<Ticket> findByIdComplete(@Param("id") Long id);

    @Query("SELECT T FROM Ticket T WHERE T.status = :status AND T.trip.id = :tripId")
    List<Ticket> findByStatusAndTripId(@Param("status") TicketStatus status, @Param("tripId") Long tripId);

    @Query("SELECT T FROM Ticket T WHERE T.fromStop.id = :fromStopId AND T.toStop.id = :toStopId AND T.trip.id = :tripId")
    List<Ticket> findByStopsAndTrip(@Param("fromStopId") Long fromStopId,
            @Param("toStopId") Long toStopId, @Param("tripId") Long tripId);

    boolean existsByTripIdAndSeatNumber(Long tripId, String seatNumber);
}
