package co.edu.unimagdalena.busesreservationandparcels.domain.repositories;

import co.edu.unimagdalena.busesreservationandparcels.domain.entities.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface ConfigRepository extends JpaRepository<Config, String> {
    Optional<Config> findByKeyIgnoreCase(String key);

    @Modifying
    void deleteByKeyIgnoreCase(String key);
}
