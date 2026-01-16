package co.edu.unimagdalena.busesreservationandparcels.services.impl;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.BusDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.entities.Amenity;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.BusStatus;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.AmenityRepository;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.BusRepository;
import co.edu.unimagdalena.busesreservationandparcels.exceptions.AlreadyExistsException;
import co.edu.unimagdalena.busesreservationandparcels.exceptions.NotFoundException;
import co.edu.unimagdalena.busesreservationandparcels.services.BusService;
import co.edu.unimagdalena.busesreservationandparcels.services.mappers.BusMapper;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusServiceImpl implements BusService {
    private final BusRepository busRepo;
    private final BusMapper mapper;
    private final AmenityRepository amenityRepo;

    @Override
    @Transactional
    public BusResponse create(BusCreateRequest request) {
        if (busRepo.existsByPlate(request.plate()))
            throw new AlreadyExistsException("Bus with plate %s already exists.".formatted(request.plate()));

        var bus = mapper.toEntity(request);

        if (!request.amenityIds().isEmpty()) {
            Set<Amenity> amenities = bringAmenities(request.amenityIds());
            bus.setAmenities(amenities);
        }

        return mapper.toResponse(busRepo.save(bus));
    }

    private Set<Amenity> bringAmenities(@NotNull Set<Long> ids) {
        var amenities = amenityRepo.findAllById(ids);
        if (amenities.size() != ids.size()) {
            var foundIds = amenities.stream().map(Amenity::getId)
                    .collect(Collectors.toSet());

            ids.removeAll(foundIds);
            throw new NotFoundException("Amenities not found: " + ids);
        }
        return new HashSet<>(amenities);
    }

    @Override
    public BusResponse get(Long id) {
        var bus = busRepo.findById(id).orElseThrow(
                () -> new NotFoundException("Bus with id %d not found.".formatted(id)));
        return mapper.toResponse(bus);
    }

    @Override
    @Transactional
    public BusResponse update(Long id, BusUpdateRequest request) {
        var bus = busRepo.findById(id).orElseThrow(
                () -> new NotFoundException("Bus with id %d not found.".formatted(id)));
        mapper.patch(request, bus);

        if (request.amenityIds() != null) {
            if (request.amenityIds().isEmpty()) bus.setAmenities(new HashSet<>());
            else {
                var amenities = bringAmenities(request.amenityIds());
                bus.setAmenities(amenities);
            }
        }
        return mapper.toResponse(busRepo.save(bus));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        busRepo.deleteById(id);
    }

    @Override
    public BusResponse getByPlate(String plate) {
        var bus = busRepo.findByPlate(plate).orElseThrow(
                () -> new NotFoundException("Bus with plate %s.".formatted(plate))
        );
        return mapper.toResponse(bus);
    }

    @Override
    public List<BusResponse> getByStatus(BusStatus status) {
        return busRepo.findByStatus(status).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<BusResponse> getByCapacityGreaterThan(Integer capacity) {
        return busRepo.findByCapacityGreaterThanEqual(capacity).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public BusResponse getWithDetails(Long id) {
        var bus = busRepo.findByIdWithAmenities(id).orElseThrow(
                () -> new NotFoundException("Bus with id %d not found.".formatted(id))
        );
        return mapper.toResponse(bus);
    }

    @Override
    public List<BusResponse> getByAmenities(Set<String> amenities) {
        return busRepo.findAllWithAmenities().stream()
                .map(mapper::toResponse)
                .toList();
    }
}
