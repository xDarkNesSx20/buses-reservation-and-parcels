package co.edu.unimagdalena.busesreservationandparcels.domain.repositories;

import co.edu.unimagdalena.busesreservationandparcels.domain.entities.Baggage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BaggageRepository extends JpaRepository<Baggage, Long> {
    List<Baggage> findByTicket_Id(Long ticketId);
    Optional<Baggage> findByTagCode(String tagCode);

    Page<Baggage> findByWeightKgGreaterThanEqual(BigDecimal minWeight, Pageable pageable);
    long countByTrunkNumber(Integer trunkNumber);
}
