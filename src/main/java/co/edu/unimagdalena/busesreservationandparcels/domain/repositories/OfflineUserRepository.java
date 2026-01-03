package co.edu.unimagdalena.busesreservationandparcels.domain.repositories;

import co.edu.unimagdalena.busesreservationandparcels.domain.entities.OfflineUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface OfflineUserRepository extends JpaRepository<OfflineUser, Long> {
    List<OfflineUser> findByFullNameContainingIgnoreCase(String fullName);
    Page<OfflineUser> findByCreatedAtBetween(OffsetDateTime from, OffsetDateTime to, Pageable pageable);
    Optional<OfflineUser> findByIdNumber(String idNumber);
}
