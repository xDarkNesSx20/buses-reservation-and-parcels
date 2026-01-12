package co.edu.unimagdalena.busesreservationandparcels.services;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.AppUserDTOs.*;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;

public interface AppUserService {
    AppUserResponse create(AppUserCreateRequest request);

    AppUserResponse get(Long id);

    AppUserResponse update(Long id, AppUserUpdateRequest request);

    void delete(Long id);

    List<AppUserResponse> getByNameContaining(String name);

    AppUserResponse getByEmail(String email);

    List<AppUserResponse> getByRole(Role role);

    List<AppUserResponse> getByActive(Boolean active);

    Page<AppUserResponse> getByCreatedAtBetween(OffsetDateTime from, OffsetDateTime to, Pageable pageable);
}
