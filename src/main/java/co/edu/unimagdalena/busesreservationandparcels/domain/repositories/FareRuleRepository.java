package co.edu.unimagdalena.busesreservationandparcels.domain.repositories;

import co.edu.unimagdalena.busesreservationandparcels.domain.entities.FareRule;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FareRuleRepository extends JpaRepository<FareRule, Long> {

    @Query("SELECT FR FROM FareRule FR JOIN FETCH FR.fromStop JOIN FETCH FR.toStop WHERE FR.route.id = :routeId")
    List<FareRule> findByRoute_Id(Long routeId);

    @Query("SELECT FR FROM FareRule FR LEFT JOIN FETCH FR.discounts WHERE FR.fromStop.id = :fromStop AND FR.toStop.id = :toStop")
    Optional<FareRule> findByFromStop_IdAndToStop_Id(@Param("fromStop") Long fromStop, @Param("toStop") Long toStop);

    @EntityGraph(attributePaths = {"fromStop", "toStop", "discounts"})
    List<FareRule> findByDynamicPricingTrue();

    @EntityGraph(attributePaths = {"fromStop", "toStop", "discounts", "route"})
    @Query("SELECT FR FROM FareRule FR WHERE FR.id = :id")
    Optional<FareRule> findByIdWithAllDetails(@Param("id") Long id);
}
