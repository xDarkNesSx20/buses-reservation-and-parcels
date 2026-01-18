package co.edu.unimagdalena.busesreservationandparcels.domain.repositories;

import co.edu.unimagdalena.busesreservationandparcels.domain.entities.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StopRepository extends JpaRepository<Stop, Long> {
    List<Stop> findByRoute_Id(Long routeId);
    List<Stop> findByNameIgnoreCase(String name);
    Optional<Stop> findByRoute_IdAndStopOrder(Long routeId, Integer stopOrder);

    @Query(value = "SELECT * FROM stops WHERE route_id = :routeId ORDER BY stop_order DESC LIMIT 1", nativeQuery = true)
    Optional<Stop> findLastRouteStop(@Param("routeId") Long routeId);

    @Query(value = "SELECT * FROM stops WHERE route_id = :routeId ORDER BY stop_order LIMIT 1", nativeQuery = true)
    Optional<Stop> findFirstRouteStop(@Param("routeId") Long routeId);

    @Query("SELECT S FROM Stop S JOIN FETCH S.route WHERE S.id = :id")
    Optional<Stop> findByIdWithRoute(@Param("id") Long id);

    boolean existsById(Long id);

    boolean existsByRoute_IdAndStopOrder(Long routeId, Integer stopOrder);

    List<Stop> findByRoute_IdAndStopOrderGreaterThanEqual(Long routeId, Integer minStopOrder);
}
