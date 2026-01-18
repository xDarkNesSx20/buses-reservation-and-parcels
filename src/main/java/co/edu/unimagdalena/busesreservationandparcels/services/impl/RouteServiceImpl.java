package co.edu.unimagdalena.busesreservationandparcels.services.impl;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.RouteDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.RouteRepository;
import co.edu.unimagdalena.busesreservationandparcels.exceptions.NotFoundException;
import co.edu.unimagdalena.busesreservationandparcels.services.RouteService;
import co.edu.unimagdalena.busesreservationandparcels.services.mappers.RouteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    private final RouteRepository routeRepo;
    private final RouteMapper mapper;

    @Override
    @Transactional
    public RouteResponse create(RouteCreateRequest request) {
        var route = mapper.toEntity(request);
        route.setCode(generateCode(request));
        return mapper.toResponse(routeRepo.save(route));
    }

    private String generateCode(RouteCreateRequest request){
        return "%s-%s-%s".formatted(request.origin().substring(0,3).toUpperCase(),
                request.destination().substring(0,3).toUpperCase(),
                UUID.randomUUID().toString().substring(0, 8).toUpperCase()
        );
    }

    @Override
    public RouteResponse get(Long id) {
        var route = routeRepo.findById(id).orElseThrow(
                () -> new NotFoundException("Route with id %d not found.".formatted(id))
        );
        return mapper.toResponse(route);
    }

    @Override
    @Transactional
    public RouteResponse update(Long id, RouteUpdateRequest request) {
        var route = routeRepo.findById(id).orElseThrow(
                () -> new NotFoundException("Route with id %d not found.".formatted(id))
        );
        mapper.patch(request, route);

        if (request.destination() != null){
            var codeParts = route.getCode().split("-");
            codeParts[1] = request.destination().substring(0,3).toUpperCase();
            route.setCode(String.join("-", codeParts));
        }

        return mapper.toResponse(routeRepo.save(route));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        routeRepo.deleteById(id);
    }

    @Override
    public RouteResponse getByName(String name) {
        var route = routeRepo.findByNameIgnoreCase(name).orElseThrow(
                () -> new NotFoundException("Route with name %s not found.".formatted(name))
        );
        return mapper.toResponse(route);
    }

    @Override
    public RouteResponse getByCode(String code) {
        var route = routeRepo.findByCode(code).orElseThrow(
                () -> new NotFoundException("Route with code %s not found.".formatted(code))
        );
        return mapper.toResponse(route);
    }

    @Override
    public RouteResponse getShortest() {
        var route = routeRepo.findShortestRoute().orElseThrow(
                () -> new NotFoundException("No routes found.")
        );
        return mapper.toResponse(route);
    }

    @Override
    public RouteResponse getLongest() {
        var route = routeRepo.findLongestRoute().orElseThrow(
                () -> new NotFoundException("No routes found.")
        );
        return mapper.toResponse(route);
    }

    @Override
    public List<RouteResponse> getAll() {
        return routeRepo.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<RouteResponse> getByOrigin(String origin) {
        return routeRepo.findByOrigin(origin).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<RouteResponse> getByDestination(String destination) {
        return routeRepo.findByDestination(destination).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<RouteResponse> getByOriginAndDestination(String origin, String destination) {
        return routeRepo.findByOriginAndDestination(origin, destination).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<RouteResponse> getByDurationGreaterThanEqual(Integer minDuration) {
        return routeRepo.findByDurationMinGreaterThanEqual(minDuration).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<RouteResponse> getByDurationLessThanEqual(Integer maxDuration) {
        return routeRepo.findByDurationMinLessThanEqual(maxDuration).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<RouteResponse> getByDistanceGreaterThanEqual(BigDecimal minDistanceKm) {
        return routeRepo.findByDistanceKmGreaterThanEqual(minDistanceKm).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<RouteResponse> getByDistanceLessThanEqual(BigDecimal maxDistanceKm) {
        return routeRepo.findByDistanceKmLessThanEqual(maxDistanceKm).stream()
                .map(mapper::toResponse)
                .toList();
    }
}
