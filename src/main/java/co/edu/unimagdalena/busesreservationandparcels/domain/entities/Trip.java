package co.edu.unimagdalena.busesreservationandparcels.domain.entities;

import co.edu.unimagdalena.busesreservationandparcels.domain.enums.TripStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trips")
public class Trip {
    @Id
    @Column(name = "trip_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id")
    private Bus bus;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, name = "departure_at")
    private OffsetDateTime departureAt;

    @Column(nullable = false, name = "arrival_eta")
    private OffsetDateTime arrivalEta;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TripStatus status = TripStatus.SCHEDULED;
}
