package co.edu.unimagdalena.busesreservationandparcels.api.dto;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.CommonResponses.TripSummary;
import co.edu.unimagdalena.busesreservationandparcels.api.dto.CommonResponses.UserSummary;
import java.io.Serializable;
import java.time.OffsetDateTime;

public class AssignmentDTOs {
    public record AssignmentCreateRequest(Long driverId, Long dispatcherId) implements Serializable {
    }

    public record AssignmentUpdateRequest(Long driverId, Long dispatcherId,
                                          Boolean checkListOk) implements Serializable {
    }

    public record AssignmentResponse(Long id, TripSummary trip, UserSummary driver, UserSummary dispatcher,
                                     OffsetDateTime assignedAt, Boolean checkListOk) implements Serializable {
    }
}
