package co.edu.unimagdalena.busesreservationandparcels.domain.repositories;

import co.edu.unimagdalena.busesreservationandparcels.domain.entities.Trip;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.TripStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip, Long> {
    @Query("SELECT T FROM Trip T LEFT JOIN FETCH T.bus WHERE T.route.id = :routeId")
    List<Trip> findByRoute_Id(@Param("routeId") Long routeId);

    @Query("SELECT T FROM Trip T LEFT JOIN FETCH T.bus WHERE T.status = :status")
    List<Trip> findByStatus(@Param("status") TripStatus status);

    @Query("SELECT T FROM Trip T LEFT JOIN FETCH T.bus WHERE T.date = :date")
    List<Trip> findByDate(@Param("date") LocalDate date);

    @Query("SELECT T FROM Trip T LEFT JOIN FETCH T.bus WHERE T.date BETWEEN :from AND :to")
    Page<Trip> findByDateBetween(@Param("from") LocalDate from, @Param("to") LocalDate to, Pageable pageable);

    @Query("SELECT T FROM Trip T LEFT JOIN FETCH T.bus WHERE T.departureAt BETWEEN :from AND :to")
    List<Trip> findByDepartureAtBetween(@Param("from") OffsetDateTime from, @Param("to") OffsetDateTime to);

    @Query("SELECT T FROM Trip T LEFT JOIN FETCH T.bus WHERE T.route.id = :routeId AND T.status = :status")
    List<Trip> findByRoute_IdAndStatus(@Param("routeId") Long routeId, @Param("status") TripStatus status);

    @Query("SELECT T FROM Trip T LEFT JOIN FETCH T.bus WHERE T.route.id = :routeId AND T.date = :date")
    List<Trip> findByRoute_IdAndDate(@Param("routeId") Long routeId, @Param("date") LocalDate date);

    @Query("SELECT T FROM Trip T JOIN FETCH T.route LEFT JOIN FETCH T.bus WHERE T.id = :id ")
    Optional<Trip> findByIdWithDetails(@Param("id") Long id);

    @Query(value = """
        SELECT COALESCE(B.capacity, 30) - COALESCE(ticket_count, 0) - COALESCE(seat_hold_count, 0)
                AS available_seats
        FROM trips Tr LEFT JOIN buses B ON B.bus_id = Tr.bus_id
        LEFT JOIN (
            SELECT trip_id, COUNT(DISTINCT seat_number) AS ticket_count
            FROM tickets WHERE status NOT IN ('NO_SHOW', 'CANCELLED')
            GROUP BY trip_id
        ) T ON T.trip_id = Tr.trip_id
        LEFT JOIN (
            SELECT trip_id, COUNT(DISTINCT seat_number) AS seat_hold_count
            FROM seats_hold WHERE status = 'HOLD' AND expires_at > CURRENT_TIMESTAMP
            GROUP BY trip_id
        ) Sh ON Sh.trip_id = Tr.trip_id
        WHERE Tr.trip_id = :tripId
        """, nativeQuery = true)
    int countFreeSeats(@Param("tripId") Long tripId);
}
