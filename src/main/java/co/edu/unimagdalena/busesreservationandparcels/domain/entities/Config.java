package co.edu.unimagdalena.busesreservationandparcels.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "configs")
public class Config {
    @Id
    @Column(nullable = false, unique = true)
    private String key;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal value;
}
