package co.edu.unimagdalena.busesreservationandparcels.services.impl;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.StopDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.RouteRepository;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.StopRepository;
import co.edu.unimagdalena.busesreservationandparcels.exceptions.NotFoundException;
import co.edu.unimagdalena.busesreservationandparcels.services.StopService;
import co.edu.unimagdalena.busesreservationandparcels.services.mappers.StopMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StopServiceImpl implements StopService {
    private final StopRepository stopRepo;
    private final StopMapper mapper;
    private final RouteRepository routeRepo;

    @Override
    @Transactional
    public StopFullResponse create(Long routeId, StopCreateRequest request) {
        var route = routeRepo.findById(routeId).orElseThrow(
                () -> new NotFoundException("Route with id %d not found.".formatted(routeId))
        );
        var stop = mapper.toEntity(request);
        stop.setRoute(route);

        if (request.stopOrder() == null) {
            var lastStop = stopRepo.findLastRouteStop(routeId);
            stop.setStopOrder(lastStop.map(s -> s.getStopOrder() + 1).orElse(0));
        } else {
            if (stopRepo.existsByRoute_IdAndStopOrder(routeId, request.stopOrder()))
                updateStopOrdersAfterInsert(routeId, request.stopOrder());
        }

        return mapper.toFullResponse(stopRepo.save(stop));
    }

    private void updateStopOrdersAfterInsert(Long routeId, Integer fromOrder) {
        var stopsToUpdate = stopRepo.findByRoute_IdAndStopOrderGreaterThanEqual(routeId, fromOrder);
        stopsToUpdate.forEach(s -> s.setStopOrder(s.getStopOrder() + 1));
        stopRepo.saveAll(stopsToUpdate);
    }

    @Override
    @Transactional
    public List<StopResponse> createAll(Long routeId, Set<StopCreateRequest> requests) {
        var route = routeRepo.findById(routeId).orElseThrow(
                () -> new NotFoundException("Route with id %d not found.".formatted(routeId))
        );
        var stops = requests.stream()
                .map(req -> {
                    var stop = mapper.toEntity(req);
                    stop.setRoute(route);
                    return stop;
                }).toList();
        return stopRepo.saveAll(stops).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public StopResponse get(Long id) {
        var stop = stopRepo.findById(id).orElseThrow(
                () -> new NotFoundException("Stop with id %d not found.".formatted(id))
        );
        return mapper.toResponse(stop);
    }

    @Override
    @Transactional
    public StopResponse update(Long id, StopUpdateRequest request) {
        var stop = stopRepo.findById(id).orElseThrow(
                () -> new NotFoundException("Stop with id %d not found.".formatted(id))
        );
        mapper.patch(request, stop);
        return mapper.toResponse(stopRepo.save(stop));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        stopRepo.deleteById(id);
    }

    @Override
    public List<StopResponse> getByRoute(Long routeId) {
        if (!routeRepo.existsById(routeId))
            throw new NotFoundException("Route with id %d not found.".formatted(routeId));

        return stopRepo.findByRoute_Id(routeId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<StopResponse> getByName(String name) {
        return stopRepo.findByNameIgnoreCase(name).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public StopResponse getByRouteAndOrder(Long routeId, Integer stopOrder) {
        var stop = stopRepo.findByRoute_IdAndStopOrder(routeId, stopOrder).orElseThrow(
                () -> new NotFoundException("Stop with order %d for route %d not found.".formatted(stopOrder, routeId))
        );
        return mapper.toResponse(stop);
    }

    @Override
    public StopResponse getLastByRoute(Long routeId) {
        if (routeRepo.existsById(routeId))
            throw new NotFoundException("Route with id %d not found.".formatted(routeId));
        var stop = stopRepo.findLastRouteStop(routeId).orElseThrow(
                () -> new NotFoundException("No stops found for route with id %d.".formatted(routeId)));
        return mapper.toResponse(stop);
    }

    @Override
    public StopResponse getFirstByRoute(Long routeId) {
        if (routeRepo.existsById(routeId))
            throw new NotFoundException("Route with id %d not found.".formatted(routeId));
        var stop = stopRepo.findFirstRouteStop(routeId).orElseThrow(
                () -> new NotFoundException("No stops found for route with id %d.".formatted(routeId)));
        return mapper.toResponse(stop);
    }

    @Override
    public StopFullResponse getWithDetails(Long id) {
        var stop = stopRepo.findByIdWithRoute(id).orElseThrow(
                () -> new NotFoundException("Stop with id %d not found.".formatted(id))
        );
        return mapper.toFullResponse(stop);
    }
}
