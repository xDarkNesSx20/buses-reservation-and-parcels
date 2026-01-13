package co.edu.unimagdalena.busesreservationandparcels.services;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.SeatDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.SeatType;

import java.util.List;
import java.util.Set;

public interface SeatService {
    SeatFullResponse create(Long busId, SeatCreateRequest request);
    List<SeatResponse> createAll(Long busId, Set<SeatCreateRequest> requests);
    SeatResponse get(Long id);
    SeatResponse update(Long id, SeatUpdateRequest request);
    void delete(Long id);

    List<SeatResponse> getByBus(Long busId);
    List<SeatResponse> getByBusAndType(Long busId, SeatType type);
    SeatResponse getByBusAndNumber(Long busId, String number);
    SeatFullResponse getWithDetails(Long id);
}
