package co.edu.unimagdalena.busesreservationandparcels.services.impl;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.AppUserDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.Role;
import co.edu.unimagdalena.busesreservationandparcels.domain.repositories.AppUserRepository;
import co.edu.unimagdalena.busesreservationandparcels.exceptions.AlreadyExistsException;
import co.edu.unimagdalena.busesreservationandparcels.exceptions.NotFoundException;
import co.edu.unimagdalena.busesreservationandparcels.services.AppUserService;
import co.edu.unimagdalena.busesreservationandparcels.services.mappers.AppUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepo;
    private final AppUserMapper mapper;

    /*
    This method ain't needed 'cause AuthController will take it on
    @Override
    @Transactional
    public AppUserResponse create(@NotNull AppUserCreateRequest request) {
        var alreadyExists = appUserRepo.existsByEmailIgnoreCase(request.email());
        if (alreadyExists) throw new AlreadyExistsException("Email already in use.");

        var appUser = mapper.toEntity(request);
    }*/

    @Override
    public AppUserResponse get(Long id) {
        var appUser = appUserRepo.findById(id).orElseThrow(
                () -> new NotFoundException("AppUser with id %d not found.".formatted(id))
        );
        return mapper.toResponse(appUser);
    }

    @Override
    @Transactional
    public AppUserResponse update(Long id, AppUserUpdateRequest request) {
        var appUser = appUserRepo.findById(id).orElseThrow(
                () -> new NotFoundException("AppUser with id %d not found.".formatted(id))
        );

        if (request.email() != null){
            var emailInUse = appUserRepo.existsByEmailIgnoreCase(request.email());
            if (emailInUse) throw new AlreadyExistsException("Email already in use.");
        }
        mapper.patch(request, appUser);

        if (request.password() != null){
            //And now, how can I encrypt the password?
        }
        return mapper.toResponse(appUserRepo.save(appUser));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        appUserRepo.deleteById(id);
    }

    @Override
    public List<AppUserResponse> getByNameContaining(String name) {
        return appUserRepo.findByFullNameContainingIgnoreCase(name).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public AppUserResponse getByEmail(String email) {
        var appUser = appUserRepo.findByEmailIgnoreCase(email).orElseThrow(
                () -> new NotFoundException("AppUser with email %s not found.".formatted(email))
        );
        return mapper.toResponse(appUser);
    }

    @Override
    public List<AppUserResponse> getByRole(Role role) {
        return appUserRepo.findByRole(role).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<AppUserResponse> getByActive(Boolean active) {
        return appUserRepo.findByActive(active).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public Page<AppUserResponse> getByCreatedAtBetween(OffsetDateTime from, OffsetDateTime to,
                                                       Pageable pageable) {
        if (to.isAfter(from))
            throw new IllegalArgumentException("'From' date must be after 'to' date.");

        var appUsers = appUserRepo.findByCreatedAtBetween(from, to, pageable);
        return appUsers.map(mapper::toResponse);
    }
}
