package co.edu.unimagdalena.busesreservationandparcels.services;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.ParcelDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.ParcelStatus;

import java.util.List;

public interface ParcelService {
    ParcelFullResponse create(ParcelCreateRequest request);
    ParcelResponse get(Long id);
    ParcelResponse update(Long id, ParcelUpdateRequest request);
    void delete(Long id);

    ParcelFullResponse getWithDetails(Long id);
    ParcelFullResponse getByDeliveryOTP(String deliveryOTP);
    ParcelFullResponse getByCode(String code);

    List<ParcelResponse> getByFromStop(Long fromStopId);
    List<ParcelResponse> getByToStop(Long toStopId);
    List<ParcelResponse> getByStops(Long fromStopId, Long stopId);
    List<ParcelResponse> getByStopsAndStatus(Long fromStopId, Long stopId, ParcelStatus status);
    List<ParcelResponse> getByFromStopAndStatus(Long fromStopId, ParcelStatus status);
    List<ParcelResponse> getByToStopAndStatus(Long toStopId, ParcelStatus status);
    List<ParcelResponse> getBySender(Long senderId);
    List<ParcelResponse> getByReceiver(Long senderId);
    List<ParcelResponse> getByStatus(ParcelStatus status);

    List<ParcelResponse> getIncoming(Long toStopId);
    List<ParcelResponse> getWaitingToGetInTransit(Long fromStopId);
}
