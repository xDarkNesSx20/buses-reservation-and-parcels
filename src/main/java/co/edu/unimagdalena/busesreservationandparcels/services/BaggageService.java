package co.edu.unimagdalena.busesreservationandparcels.services;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.BaggageDTOs.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface BaggageService {
    BaggageResponse create(Long ticketId, BaggageCreateRequest request);

    List<BaggageResponse> createAll(Long ticketId, Set<BaggageCreateRequest> requests);

    BaggageResponse get(Long id);

    BaggageResponse update(Long id, BaggageUpdateRequest request);

    void delete(Long id);

    List<BaggageResponse> getByTicket(Long ticketId);

    BaggageResponse getByTagCode(String tagCode);

    Page<BaggageResponse> getByWeightGreaterThan(BigDecimal minWeight, Pageable pageable);

    long countByTrunkNumber(Integer trunkNumber);
}
