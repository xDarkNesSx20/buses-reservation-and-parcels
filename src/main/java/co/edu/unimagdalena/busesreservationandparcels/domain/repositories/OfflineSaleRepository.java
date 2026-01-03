package co.edu.unimagdalena.busesreservationandparcels.domain.repositories;

import co.edu.unimagdalena.busesreservationandparcels.domain.entities.OfflineSale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfflineSaleRepository extends JpaRepository<OfflineSale, Long> {

}
