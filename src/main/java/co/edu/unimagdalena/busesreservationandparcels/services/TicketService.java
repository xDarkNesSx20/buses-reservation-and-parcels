package co.edu.unimagdalena.busesreservationandparcels.services;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.TicketDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.TicketStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface TicketService {
    TicketFullResponse createInApp(Long tripId, TicketAppCreateRequest request);
    List<TicketResponse> createAllInApp(Long tripId, Set<TicketAppCreateRequest> requests);
    TicketFullResponse createInOffice(Long tripId, TicketOfficeCreateRequest request);
    List<TicketResponse> createAllInOffice(Long tripId, Set<TicketOfficeCreateRequest> requests);

    TicketResponse get(Long id);
    TicketResponse update(Long id, TicketUpdateRequest request);
    void delete(Long id);

    List<TicketBasicResponse> getByTrip(Long tripId);
    List<TicketBasicResponse> getByPassenger(Long passengerId);
    TicketBasicResponse getByTripAndSeat(Long tripId, String seatNumber);
    List<TicketBasicResponse> getByStatusAndTrip(TicketStatus status, Long tripId);
    List<TicketBasicResponse> getByTripAndStops(Long tripId, Long fromStopId, Long toStopId);

    TicketResponse getWithStops(Long id);
    List<TicketResponse> getByTripWithStops(Long tripId);
    List<TicketResponse> getByPassengerWithStops(Long passengerId);
    Page<TicketResponse> getByStatusWithStops(TicketStatus status, Pageable pageable);

    TicketWithTripResponse getWithTripAndStops(Long id);
    List<TicketWithTripResponse> getByPassengerWithTripAndStops(Long passengerId);

    TicketWithPassengerResponse getWithPassengerAndStops(Long id);
    List<TicketWithPassengerResponse> getByTripWithPassengerAndStops(Long tripId);

    TicketFullResponse getFullTicket(Long id);
}
