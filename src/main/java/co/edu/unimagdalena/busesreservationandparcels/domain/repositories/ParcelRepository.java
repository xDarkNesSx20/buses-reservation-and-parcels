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

    @EntityGraph(attributePaths = {"sender", "receiver"})
    Optional<Parcel> findByCode(String code);

    @EntityGraph(attributePaths = {"sender", "receiver"})
    Optional<Parcel> findByDeliveryOTP(String deliveryOTP);

    List<Parcel> findByFromStop_Id(Long fromStopId);
    List<Parcel> findByToStop_Id(Long toStopId);

    List<Parcel> findByFromStop_IdAndToStop_Id(Long fromStopId, Long toStopId);
    List<Parcel> findByFromStop_IdAndToStop_IdAndStatus(Long fromStopId, Long toStopId, ParcelStatus status);
    List<Parcel> findByFromStop_IdAndStatus(Long fromStopId, ParcelStatus status);

    List<Parcel> findByToStop_IdAndStatus(Long toStopId, ParcelStatus status);

    List<Parcel> findBySender_Id(Long senderId);

    List<Parcel> findByReceiver_Id(Long receiverId);
    List<Parcel> findByStatus(ParcelStatus status);

    @Query("SELECT P FROM Parcel P WHERE P.toStop.id = :toStopId AND P.status IN ('CREATED', 'IN_TRANSIT')")
    List<Parcel> getIncomingParcels(@Param("toStopId") Long toStopId);

    @Query("SELECT P FROM Parcel P WHERE P.fromStop.id = :fromStopId AND P.status = 'CREATED'")
    List<Parcel> getParcelsToPickUpInFromStop(@Param("fromStopId") Long fromStopId);
}
