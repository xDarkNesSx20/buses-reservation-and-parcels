package co.edu.unimagdalena.busesreservationandparcels.services;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.DiscountDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.DiscountType;

import java.util.List;
import java.util.Set;

public interface DiscountService {
    DiscountFullResponse create(Long fareRuleId, DiscountCreateRequest request);

    List<DiscountResponse> createAll(Long fareRuleId, Set<DiscountCreateRequest> requests);

    DiscountResponse get(Long id);

    DiscountResponse update(Long id, DiscountUpdateRequest request);

    void delete(Long id);

    DiscountFullResponse getWithDetails(Long id);

    List<DiscountFullResponse> getByTypeWithDetails(DiscountType type);

    List<DiscountResponse> getByType(DiscountType type);

    List<DiscountResponse> getByFareRule(Long fareRuleId);
}
