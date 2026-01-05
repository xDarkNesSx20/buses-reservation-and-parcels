package co.edu.unimagdalena.busesreservationandparcels.domain.repositories;

import co.edu.unimagdalena.busesreservationandparcels.domain.entities.Ticket;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.PaymentMethod;
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
    Page<Ticket> findByStatus(TicketStatus status, Pageable pageable);
    List<Ticket> findByTrip_Id(Long tripId);
    List<Ticket> findByStatusAndTrip_Id(TicketStatus status, Long tripId);
    Optional<Ticket> findByTrip_IdAndSeatNumber(Long tripId, String seatNumber);
    Page<Ticket> findByFromStop_Id(Long fromStopId, Pageable pageable);
    Page<Ticket> findByToStop_Id(Long toStopId, Pageable pageable);
    List<Ticket> findByFromStop_IdAndToStop_Id(Long fromStopId, Long toStopId);
    List<Ticket> findByFromStop_IdAndTrip_Id(Long fromStopId, Long tripId);
    List<Ticket> findByToStop_IdAndTrip_Id(Long toStopId, Long tripId);
    List<Ticket> findByFromStop_IdAndToStop_IdAndTrip_Id(Long fromStopId, Long toStopId, Long tripId);
    List<Ticket> findByPassenger_Id(Long passengerId);

    @Query("SELECT T FROM Ticket T JOIN FETCH T.trip WHERE T.passenger.id = :passengerId")
    List<Ticket> findByPassenger_IdWithTrips(@Param("passengerId") Long passengerId);

    Page<Ticket> findByPaymentMethod(PaymentMethod paymentMethod, Pageable pageable);

    @EntityGraph(attributePaths = {"trip", "passenger"})
    @Query("SELECT T FROM Ticket T WHERE T.id = :id")
    Optional<Ticket> findByIdWithDetails(@Param("id") Long id);
}
