package co.edu.unimagdalena.busesreservationandparcels.services;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.AssignmentDTOs.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;

public interface AssignmentService {
    AssignmentFullResponse create(Long tripId, AssignmentCreateRequest request);

    AssignmentResponse get(Long id);

    AssignmentResponse update(Long id, AssignmentUpdateRequest request);

    void delete(Long id);

    AssignmentResponse getByTrip(Long tripId);

    List<AssignmentResponse> getByCheckList(Boolean checkList);

    List<AssignmentResponse> getByDriver(Long driverId);

    List<AssignmentResponse> getByDispatcher(Long dispatcherId);

    Page<AssignmentResponse> getByAssignedAt(OffsetDateTime from, OffsetDateTime to, Pageable pageable);

    List<AssignmentFullResponse> getByDriverWithDetails(Long driverId);

    List<AssignmentFullResponse> getByDispatcherWithDetails(Long dispatcherId);
}
