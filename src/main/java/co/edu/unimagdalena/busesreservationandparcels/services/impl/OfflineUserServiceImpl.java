package co.edu.unimagdalena.busesreservationandparcels.services.impl;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.OfflineUserDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.OfflineUserRepository;
import co.edu.unimagdalena.busesreservationandparcels.exceptions.AlreadyExistsException;
import co.edu.unimagdalena.busesreservationandparcels.exceptions.NotFoundException;
import co.edu.unimagdalena.busesreservationandparcels.services.OfflineUserService;
import co.edu.unimagdalena.busesreservationandparcels.services.mappers.OfflineUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OfflineUserServiceImpl implements OfflineUserService {
    private final OfflineUserRepository offlineUserRepo;
    private final OfflineUserMapper mapper;

    @Override
    @Transactional
    public OfflineUserResponse create(OfflineUserCreateRequest request) {
        if (offlineUserRepo.existsByIdNumber(request.idNumber()))
            throw new AlreadyExistsException("User with idNumber %s already exists.".formatted(request.idNumber()));

        var offUser = mapper.toEntity(request);
        return mapper.toResponse(offlineUserRepo.save(offUser));
    }

    @Override
    public OfflineUserResponse get(Long id) {
        var offUser = offlineUserRepo.findById(id).orElseThrow(
                () -> new NotFoundException("User with id %d not found.".formatted(id))
        );
        return mapper.toResponse(offUser);
    }

    @Override
    @Transactional
    public OfflineUserResponse update(Long id, OfflineUserUpdateRequest request) {
        var offUser = offlineUserRepo.findById(id).orElseThrow(
                () -> new NotFoundException("User with id %d not found.".formatted(id))
        );
        mapper.patch(request, offUser);
        return mapper.toResponse(offlineUserRepo.save(offUser));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        offlineUserRepo.deleteById(id);
    }

    @Override
    public List<OfflineUserResponse> getByNameContaining(String name) {
        return offlineUserRepo.findByFullNameContainingIgnoreCase(name).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public Page<OfflineUserResponse> getByCreatedAt(OffsetDateTime from, OffsetDateTime to,
                                                    Pageable pageable) {
        if (to.isAfter(from))
            throw new IllegalArgumentException("'From' date must be after 'to' date.");

        return offlineUserRepo.findByCreatedAtBetween(from, to, pageable).map(mapper::toResponse);
    }

    @Override
    public OfflineUserResponse getByIdNumber(String idNumber) {
        var offUser =  offlineUserRepo.findByIdNumber(idNumber).orElseThrow(
                () -> new NotFoundException("User with idNumber %s not found.".formatted(idNumber))
        );
        return mapper.toResponse(offUser);
    }
}
