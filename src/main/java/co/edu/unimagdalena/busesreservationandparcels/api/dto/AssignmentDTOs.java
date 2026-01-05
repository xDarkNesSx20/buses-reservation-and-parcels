package co.edu.unimagdalena.busesreservationandparcels.api.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class AssignmentDTOs {
    public record AssignmentCreateRequest(Long driverId, Long dispatcherId) implements Serializable {}
    public record AssignmentUpdateRequest(Long driverId, Long dispatcherId, Boolean checkListOk) implements Serializable {}

    //Should I make a Trip and AppUser Summary?
    public record AssignmentResponse(Long id, ..., ..., ...,OffsetDateTime assignedAt, Boolean checkListOk) implements Serializable {}
}
