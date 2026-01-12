package co.edu.unimagdalena.busesreservationandparcels.services;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.DiscountDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.DiscountType;

import java.util.List;

public interface DiscountService {
    DiscountFullResponse create(Long fareRuleId, DiscountCreateRequest request);
    DiscountResponse get(Long id);
    DiscountResponse update(Long id, DiscountCreateRequest request);
    void delete(Long id);

    DiscountFullResponse getWithDetails(Long id);
    List<DiscountFullResponse> getByTypeWithDetails(DiscountType type);
    List<DiscountResponse> getByType(DiscountType type);
    List<DiscountResponse> getByFareRule(Long fareRuleId);
}
