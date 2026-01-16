package co.edu.unimagdalena.busesreservationandparcels.services;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.BusDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.BusStatus;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface BusService {
    BusResponse create(BusCreateRequest request);

    BusResponse get(Long id);

    BusResponse update(Long id, BusUpdateRequest request);

    void delete(Long id);

    BusResponse getByPlate(String plate);

    List<BusResponse> getByStatus(BusStatus status);

    List<BusResponse> getByCapacityGreaterThan(Integer capacity);

    BusResponse getWithDetails(Long id);

    List<BusResponse> getByAmenities(Set<String> amenities);
}
