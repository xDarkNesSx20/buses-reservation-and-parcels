package co.edu.unimagdalena.busesreservationandparcels.services.impl;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.AmenityDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.AmenityRepository;
import co.edu.unimagdalena.busesreservationandparcels.exceptions.AlreadyExistsException;
import co.edu.unimagdalena.busesreservationandparcels.exceptions.NotFoundException;
import co.edu.unimagdalena.busesreservationandparcels.services.AmenityService;
import co.edu.unimagdalena.busesreservationandparcels.services.mappers.AmenityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class AmenityServiceImpl implements AmenityService {
    private final AmenityRepository amenityRepo;
    private final AmenityMapper mapper;

    @Override
    public AmenityResponse create(AmenityCreateRequest request) {
        var alreadyExists = amenityRepo.existsByNameIgnoreCase(request.name());
        if (alreadyExists) throw new AlreadyExistsException(
                "Amenity with name %s already exists.".formatted(request.name()));

        var amenity = mapper.toEntity(request);
        return mapper.toResponse(amenityRepo.save(amenity));
    }

    @Override
    public List<AmenityResponse> createAll(Set<AmenityCreateRequest> requests) {
        requests.forEach(r -> {
            var alreadyExists = amenityRepo.existsByNameIgnoreCase(r.name());
            if (alreadyExists) throw new AlreadyExistsException(
                    "Amenity with name %s already exists.".formatted(r.name()));
        });

        var amenities = requests.stream()
                .map(mapper::toEntity)
                .toList();

        return amenityRepo.saveAll(amenities).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AmenityResponse get(Long id) {
        var amenity = amenityRepo.findById(id).orElseThrow(
                () -> new NotFoundException("Amenity with id %d not found.".formatted(id))
        );
        return mapper.toResponse(amenity);
    }

    @Override
    public AmenityResponse update(Long id, AmenityUpdateRequest request) {
        var amenity = amenityRepo.findById(id).orElseThrow(
                () -> new NotFoundException("Amenity with id %d not found.".formatted(id))
        );

        mapper.patch(request, amenity);
        return mapper.toResponse(amenityRepo.save(amenity));
    }

    @Override
    public void delete(Long id) {
        amenityRepo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AmenityResponse> findAll() {
        return amenityRepo.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AmenityResponse getByName(String name) {
        var amenity = amenityRepo.findByNameIgnoreCase(name).orElseThrow(
                () -> new NotFoundException("Amenity with name %s not found.".formatted(name))
        );
        return mapper.toResponse(amenity);
    }
}
