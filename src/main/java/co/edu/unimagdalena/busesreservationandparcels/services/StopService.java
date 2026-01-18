package co.edu.unimagdalena.busesreservationandparcels.services;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.StopDTOs.*;

import java.util.List;
import java.util.Set;

public interface StopService {
    StopFullResponse create(Long routeId, StopCreateRequest request);
    List<StopResponse> createAll(Long routeId, Set<StopCreateRequest> requests);
    StopResponse get(Long id);
    StopResponse update(Long id, StopUpdateRequest request);
    void delete(Long id);

    List<StopResponse> getByRoute(Long routeId);
    List<StopResponse> getByName(String name);
    StopResponse getByRouteAndOrder(Long routeId, Integer stopOrder);
    StopResponse getLastByRoute(Long routeId);
    StopResponse getFirstByRoute(Long routeId);
    StopFullResponse getWithDetails(Long id);
}
