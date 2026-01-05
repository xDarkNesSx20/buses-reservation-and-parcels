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
    @Query("SELECT T FROM Trip T LEFT JOIN FETCH T.bus WHERE T.routeId = :routeId")
    List<Trip> findByRoute_Id(@Param("routeId") Long routeId);

    List<Trip> findByStatus(TripStatus status);
    List<Trip> findByDate(LocalDate date);
    Page<Trip> findByDateBetween(LocalDate from, LocalDate to, Pageable pageable);

    @Query("SELECT T FROM Trip T JOIN FETCH T.route LEFT JOIN FETCH T.bus WHERE T.id = :id ")
    Optional<Trip> findByIdWithDetails(@Param("id") Long id);

    List<Trip> findByDepartureAtBetween(OffsetDateTime from, OffsetDateTime to);
    List<Trip> findByRoute_IdAndStatus(Long routeId, TripStatus status);
    List<Trip> findByRoute_IdAndDate(Long routeId, LocalDate date);
}
