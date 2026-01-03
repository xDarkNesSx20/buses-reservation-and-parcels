package co.edu.unimagdalena.busesreservationandparcels.domain.repositories;

import co.edu.unimagdalena.busesreservationandparcels.domain.entities.Discount;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.DiscountType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    List<Discount> findByFareRule_Id(@Param("fareRuleId") Long fareRuleId);

    @EntityGraph(attributePaths = {"fareRule", "fareRule.fromStop", "fareRule.toStop"})
    @Query("SELECT D FROM Discount D WHERE D.type = :type")
    List<Discount> findByTypeWithDetails(@Param("type")DiscountType type);

    List<Discount> findByType(@Param("type")DiscountType type);

    @EntityGraph(attributePaths = {"fareRule", "fareRule.fromStop", "fareRule.toStop"})
    @Query("SELECT D FROM Discount D WHERE D.id = :id")
    Optional<Discount> findByIdWithDetails(@Param("id") Long id);
}
