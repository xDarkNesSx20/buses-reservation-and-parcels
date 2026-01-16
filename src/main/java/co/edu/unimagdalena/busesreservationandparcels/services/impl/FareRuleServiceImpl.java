package co.edu.unimagdalena.busesreservationandparcels.services.impl;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.FareRuleDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.FareRuleRepository;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.RouteRepository;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.StopRepository;
import co.edu.unimagdalena.busesreservationandparcels.exceptions.NotFoundException;
import co.edu.unimagdalena.busesreservationandparcels.services.FareRuleService;
import co.edu.unimagdalena.busesreservationandparcels.services.mappers.FareRuleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FareRuleServiceImpl implements FareRuleService {
    private final FareRuleRepository fareRuleRepo;
    private final FareRuleMapper mapper;
    private final RouteRepository routeRepo;
    private final StopRepository stopRepo;

    @Override
    @Transactional
    public FareRuleFullResponse create(Long routeId, FareRuleCreateRequest request) {
        var route = routeRepo.findById(routeId).orElseThrow(
                () -> new NotFoundException("Route with id %d not found.".formatted(routeId))
        );
        var fromStop = stopRepo.findById(request.fromStopId()).orElseThrow(
                () -> new NotFoundException("Stop with id %d not found.".formatted(request.fromStopId()))
        );
        var toStop = stopRepo.findById(request.toStopId()).orElseThrow(
                () -> new NotFoundException("Stop with id %d not found.".formatted(request.toStopId()))
        );

        if (!fromStop.getRoute().getId().equals(routeId) && !toStop.getRoute().getId().equals(routeId))
            throw new IllegalArgumentException("FromStop and ToStop have to be in the same route.");

        if (toStop.getStopOrder() <= fromStop.getStopOrder())
            throw new IllegalArgumentException("ToStop order must be greater than FromStop order.");

        var fareRule = mapper.toEntity(request);
        fareRule.setFromStop(fromStop);
        fareRule.setToStop(toStop);
        fareRule.setRoute(route);

        return mapper.toFullResponse(fareRuleRepo.save(fareRule));
    }

    @Override
    public FareRuleResponse get(Long id) {
        var fareRule = fareRuleRepo.findById(id).orElseThrow(
                () -> new NotFoundException("FareRule with id %d not found.".formatted(id))
        );
        return mapper.toResponse(fareRule);
    }

    @Override
    @Transactional
    public FareRuleResponse update(Long id, FareRuleUpdateRequest request) {
        var fareRule = fareRuleRepo.findById(id).orElseThrow(
                () -> new NotFoundException("FareRule with id %d not found.".formatted(id))
        );
        mapper.patch(request, fareRule);
        return mapper.toResponse(fareRuleRepo.save(fareRule));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        fareRuleRepo.deleteById(id);
    }

    @Override
    public List<FareRuleResponse> getByRoute(Long routeId) {
        return fareRuleRepo.findByRoute_Id(routeId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public FareRuleFullResponse getWithDetails(Long id) {
        var fareRule =  fareRuleRepo.findByIdWithAllDetails(id).orElseThrow(
                () -> new NotFoundException("FareRule with id %d not found.".formatted(id))
        );
        return mapper.toFullResponse(fareRule);
    }

    @Override
    public List<FareRuleResponse> getByDynamicPricingActive() {
        return fareRuleRepo.findByDynamicPricingTrue().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public FareRuleBasicResponse getByStops(Long fromStopId, Long toStopId) {
        var fromStop =  stopRepo.findById(fromStopId).orElseThrow(
                () -> new NotFoundException("Stop with id %d not found.".formatted(fromStopId))
        );
        var toStop =  stopRepo.findById(toStopId).orElseThrow(
                () -> new NotFoundException("Stop with id %d not found.".formatted(toStopId))
        );
        if (!fromStop.getRoute().getId().equals(toStop.getRoute().getId()))
            throw new IllegalArgumentException("FromStop and ToStop have to be in the same route.");

        if (toStop.getStopOrder() <= fromStop.getStopOrder())
            throw new IllegalArgumentException("ToStop order must be greater than FromStop order.");

        return fareRuleRepo.findByFromStop_IdAndToStop_Id(fromStopId, toStopId)
                .map(mapper::toBasicResponse)
                .orElseThrow(() -> new NotFoundException(
                        "FareRule with fromStop %d and toStop %d not found.".formatted(fromStopId, toStopId))
                );
    }
}
