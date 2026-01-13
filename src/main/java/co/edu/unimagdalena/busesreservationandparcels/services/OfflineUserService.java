package co.edu.unimagdalena.busesreservationandparcels.services;

import co.edu.unimagdalena.busesreservationandparcels.api.dto.OfflineUserDTOs.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;

public interface OfflineUserService {
    OfflineUserResponse create(OfflineUserCreateRequest request);

    OfflineUserResponse get(Long id);

    OfflineUserResponse update(Long id, OfflineUserUpdateRequest request);

    void delete(Long id);

    List<OfflineUserResponse> getByNameContaining(String name);

    Page<OfflineUserResponse> getByCreatedAt(OffsetDateTime from, OffsetDateTime to, Pageable pageable);

    OfflineUserResponse getByIdNumber(String idNumber);
}
