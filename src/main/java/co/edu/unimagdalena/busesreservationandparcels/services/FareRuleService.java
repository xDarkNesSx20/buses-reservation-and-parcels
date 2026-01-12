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

    //What should I do with the method: getByStops?
    // I have to use Response with stops null? Or make join fetch with them?
}
