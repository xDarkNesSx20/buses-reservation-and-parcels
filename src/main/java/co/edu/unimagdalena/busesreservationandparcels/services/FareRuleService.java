package co.edu.unimagdalena.busesreservationandparcels.services;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.FareRuleDTOs.*;

import java.util.List;

public interface FareRuleService {
    FareRuleFullResponse create(Long routeId, FareRuleCreateRequest request);

    FareRuleResponse get(Long id);

    FareRuleResponse update(Long id, FareRuleUpdateRequest request);

    void delete(Long id);

    List<FareRuleResponse> getByRoute(Long routeId);

    FareRuleFullResponse getWithDetails(Long id);

    List<FareRuleResponse> getByDynamicPricingActive();

    FareRuleBasicResponse getByStops(Long fromStopId, Long toStopId);
}
