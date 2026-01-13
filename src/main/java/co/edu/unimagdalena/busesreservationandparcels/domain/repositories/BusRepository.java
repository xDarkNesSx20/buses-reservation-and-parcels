package co.edu.unimagdalena.busesreservationandparcels.domain.repositories;

import co.edu.unimagdalena.busesreservationandparcels.domain.entities.Bus;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.BusStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BusRepository extends JpaRepository<Bus, Long> {
    @Query("SELECT B FROM Bus B LEFT JOIN FETCH B.amenities WHERE B.plate = :plate")
    Optional<Bus> findByPlate(String plate);

    List<Bus> findByCapacityGreaterThanEqual(Integer minCapacity);
    List<Bus> findByStatus(BusStatus status);
    boolean existsByPlate(String plate);

    @Query("SELECT B FROM Bus B LEFT JOIN FETCH B.amenities")
    List<Bus> findAllWithAmenities();

    @Query("SELECT B FROM Bus B LEFT JOIN FETCH B.amenities WHERE B.id = :id")
    Optional<Bus> findByIdWithAmenities(@Param("id") Long id);

    @Query(value = """
        SELECT B.* FROM buses B
        JOIN bus_amenities BA ON B.bus_id = BA.bus_id
        JOIN amenities A ON A.amenity_id = BA.amenity_id
        WHERE A.name IN :amenities
        GROUP BY B.bus_id HAVING COUNT(DISTINCT A.name) = :required
    """, nativeQuery = true)
    List<Bus> findByHavingTheseAmenities(@Param("amenities") Collection<String> amenities,
                                         @Param("required") Integer required);
}
