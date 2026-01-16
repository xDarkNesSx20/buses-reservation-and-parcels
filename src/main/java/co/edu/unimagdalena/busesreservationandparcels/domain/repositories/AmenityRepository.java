package co.edu.unimagdalena.busesreservationandparcels.domain.repositories;

import co.edu.unimagdalena.busesreservationandparcels.domain.entities.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AmenityRepository extends JpaRepository<Amenity,Long> {
    Optional<Amenity> findByNameIgnoreCase(String name);
    List<Amenity> findAllByOrderByNameAsc();
    boolean existsByNameIgnoreCase(String name);
}
