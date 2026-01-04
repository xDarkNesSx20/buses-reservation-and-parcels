package co.edu.unimagdalena.busesreservationandparcels.domain.repositories;

import co.edu.unimagdalena.busesreservationandparcels.domain.entities.Seat;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByBus_Id(Long busId);
    Optional<Seat> findByBus_IdAndNumber(Long busId, String number);
    List<Seat> findByBus_IdAndType(Long busId, SeatType type);

    @Query("SELECT S FROM Seat S JOIN FETCH S.bus WHERE S.id = :id")
    Optional<Seat> findByIdWithBus(@Param("id") Long id);

}
