package co.edu.unimagdalena.busesreservationandparcels.domain.repositories;

import co.edu.unimagdalena.busesreservationandparcels.domain.entities.Parcel;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.ParcelStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParcelRepository extends JpaRepository<Parcel, Long> {
    @EntityGraph(attributePaths = {"sender", "receiver"})
    @Query("SELECT P FROM Parcel P WHERE P.id = :id")
    Optional<Parcel> findByIdWithDetails(@Param("id") Long id);

    Optional<Parcel> findByCode(String code);
    List<Parcel> findByFromStop_Id(Long fromStopId);
    List<Parcel> findByToStop_Id(Long toStopId);
    List<Parcel> findByFromStop_IdAndToStop_Id(Long fromStopId, Long toStopId);

    @Query("SELECT P FROM Parcel P WHERE P.toStop.id = :toStopId AND P.status IN ('CREATED', 'IN_TRANSIT')")
    List<Parcel> getIncomingParcels(@Param("toStopId") Long toStopId);

    @Query("SELECT P FROM Parcel P WHERE P.fromStop.id = :fromStopId AND P.status = 'CREATED'")
    List<Parcel> getParcelsToPickUp(@Param("fromStopId") Long fromStopId);

    List<Parcel> findBySender_Id(Long senderId);
    List<Parcel> findByReceiver_Id(Long receiverId);
    List<Parcel> findByStatus(ParcelStatus status);
}
