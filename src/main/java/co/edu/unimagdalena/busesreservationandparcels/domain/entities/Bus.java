package co.edu.unimagdalena.busesreservationandparcels.domain.entities;

import co.edu.unimagdalena.busesreservationandparcels.domain.enums.BusStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "buses")
public class Bus {
    @Id
    @Column(name = "bus_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 8)
    private String plate;

    @Column(nullable = false)
    private Integer capacity;

    @ManyToMany
    @JoinTable(
            name = "bus_amenities", joinColumns = @JoinColumn(name = "bus_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    @Builder.Default
    private Set<Amenity> amenities = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private BusStatus status = BusStatus.AVAILABLE;

    public void addAmenities(Amenity... amenities) {
        this.amenities.addAll(List.of(amenities));
    }

    public void clearAmenities() {
        this.amenities.clear();
    }
}
