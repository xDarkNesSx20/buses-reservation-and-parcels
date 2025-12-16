package co.edu.unimagdalena.busesreservationandparcels.domain.entities;

import co.edu.unimagdalena.busesreservationandparcels.domain.enums.SeatHoldStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seats_hold")
public class SeatHold {
    @Id
    @Column(name = "seat_hold_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @Column(name = "seat_number", length = 5, nullable = false)
    private String seatNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "passenger_id")
    private AppUser passenger;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private SeatHoldStatus status = SeatHoldStatus.HOLD;
}
