package co.edu.unimagdalena.busesreservationandparcels.services;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.AmenityDTOs.*;

import java.util.List;

public interface AmenityService {
    AmenityResponse create(AmenityCreateRequest request);

    AmenityResponse get(Long id);

    AmenityResponse update(Long id, AmenityUpdateRequest request);

    void delete(Long id);

    List<AmenityResponse> findAll();

    AmenityResponse getByName(String name);
}
