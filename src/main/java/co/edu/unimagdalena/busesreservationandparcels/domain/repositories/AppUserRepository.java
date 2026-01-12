package co.edu.unimagdalena.busesreservationandparcels.domain.repositories;

import co.edu.unimagdalena.busesreservationandparcels.domain.entities.AppUser;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    List<AppUser> findByFullNameContainingIgnoreCase(String fullName);
    Page<AppUser> findByCreatedAtBetween(OffsetDateTime from, OffsetDateTime to, Pageable pageable);
    Optional<AppUser> findByEmailIgnoreCase(String email);
    List<AppUser> findByActive(Boolean active);
    List<AppUser> findByRole(Role role);
}
