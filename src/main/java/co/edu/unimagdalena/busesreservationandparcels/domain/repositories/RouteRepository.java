package co.edu.unimagdalena.busesreservationandparcels.domain.repositories;

import co.edu.unimagdalena.busesreservationandparcels.domain.entities.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RouteRepository extends JpaRepository<Route, Long> {
    Optional<Route> findByCode(String code);
    Optional<Route> findByName(String name);
    List<Route> findByOrigin(String origin);
    List<Route> findByDestination(String destination);
    List<Route> findByOriginAndDestination(String origin, String destination);
    List<Route> findByDurationMinGreaterThanEqual(Integer minDuration);
    List<Route> findByDurationMinLessThanEqual(Integer maxDuration);
    List<Route> findByDistanceKmGreaterThanEqual(BigDecimal minDistance);
    List<Route> findByDistanceKmLessThanEqual(BigDecimal maxDistance);

    @Query(value = "SELECT * FROM routes ORDER BY distanceKm LIMIT 1", nativeQuery = true)
    Optional<Route> findShortestRoute();

    @Query(value = "SELECT * FROM routes ORDER BY distanceKm DESC LIMIT 1", nativeQuery = true)
    Optional<Route> findLongestRoute();
}
