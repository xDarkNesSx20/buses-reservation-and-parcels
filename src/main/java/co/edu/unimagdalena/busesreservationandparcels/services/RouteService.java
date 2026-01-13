package co.edu.unimagdalena.busesreservationandparcels.services;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.RouteDTOs.*;

import java.math.BigDecimal;
import java.util.List;

public interface RouteService {
    RouteResponse create(RouteCreateRequest request);
    RouteResponse get(Long id);
    RouteResponse update(Long id, RouteUpdateRequest request);
    void delete(Long id);

    RouteResponse getByName(String name);
    RouteResponse getByCode(String code);
    RouteResponse getShortest();
    RouteResponse getLongest();

    List<RouteResponse> getAll();
    List<RouteResponse> getByOrigin(String origin);
    List<RouteResponse> getByDestination(String destination);
    List<RouteResponse> getByOriginAndDestination(String origin, String destination);
    List<RouteResponse> getByDurationGreaterThanEqual(Integer minDuration);
    List<RouteResponse> getByDurationLessThanEqual(Integer maxDuration);
    List<RouteResponse> getByDistanceGreaterThanEqual(BigDecimal minDistanceKm);
    List<RouteResponse> getByDistanceLessThanEqual(BigDecimal maxDistanceKm);
}
