package co.edu.unimagdalena.busesreservationandparcels.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fare_rules")
public class FareRule {
    @Id
    @Column(name = "fare_rule_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @ManyToOne
    @JoinColumn(name = "from_stop_id", nullable = false)
    private Stop fromStop;

    @ManyToOne
    @JoinColumn(name = "to_stop_id", nullable = false)
    private Stop toStop;

    @Column(name = "base_price", nullable = false, precision = 8, scale = 2)
    private BigDecimal basePrice;

    @OneToMany(mappedBy = "fareRule")
    @Builder.Default
    private Set<Discount> discounts = new HashSet<>();

    @Column(name = "dynamic_pricing", nullable = false)
    @Builder.Default
    private Boolean dynamicPricing = false;

    public void addDiscounts(Discount... discounts) {
        this.discounts.addAll(List.of(discounts));
    }

    public void clearDiscounts() {
        this.discounts.clear();
    }
}
