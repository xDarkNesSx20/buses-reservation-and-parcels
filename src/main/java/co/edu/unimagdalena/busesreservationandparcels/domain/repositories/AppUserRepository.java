package co.edu.unimagdalena.busesreservationandparcels.domain.repositories;

import co.edu.unimagdalena.busesreservationandparcels.domain.entities.AppUser;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    List<AppUser> findByFullNameContainingIgnoreCase(String fullName);
    Optional<AppUser> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
    List<AppUser> findByActive(Boolean active);
    List<AppUser> findByRole(Role role);
    Page<AppUser> findByCreatedAtBetween(OffsetDateTime from, OffsetDateTime to, Pageable pageable);
}
