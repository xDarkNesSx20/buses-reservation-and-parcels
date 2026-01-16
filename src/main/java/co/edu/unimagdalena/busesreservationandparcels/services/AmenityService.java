package co.edu.unimagdalena.busesreservationandparcels.services;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.AmenityDTOs.*;

import java.util.List;
import java.util.Set;

public interface AmenityService {
    AmenityResponse create(AmenityCreateRequest request);

    List<AmenityResponse> createAll(Set<AmenityCreateRequest> requests);

    AmenityResponse get(Long id);

    AmenityResponse update(Long id, AmenityUpdateRequest request);

    void delete(Long id);

    List<AmenityResponse> findAll();

    AmenityResponse getByName(String name);
}
