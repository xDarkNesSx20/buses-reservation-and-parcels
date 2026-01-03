package co.edu.unimagdalena.busesreservationandparcels.domain.repositories;

import co.edu.unimagdalena.busesreservationandparcels.domain.entities.Assignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    Optional<Assignment> findByTrip_Id(Long tripId);
    Page<Assignment> findByAssignedAtBetween(OffsetDateTime from, OffsetDateTime to, Pageable pageable);
    List<Assignment> findByCheckListOk(Boolean checkListOk);

    List<Assignment> findByDriver_Id(Long driverId);
    List<Assignment> findByDispatcher_Id(Long dispatcherId);

    @Query("SELECT A FROM Assignment A JOIN FETCH A.trip WHERE A.driver.id = :driverId")
    List<Assignment> findByDriver_IdWithDetails(@Param("driverId") Long driverId);

    @Query("SELECT A FROM Assignment A JOIN FETCH A.trip WHERE A.dispatcher.id = :dispatcherId")
    List<Assignment> findByDispatcher_IdWithDetails(@Param("dispatcherId") Long dispatcherId);
}
