package co.edu.unimagdalena.busesreservationandparcels.domain.entities;

import co.edu.unimagdalena.busesreservationandparcels.domain.enums.PaymentMethod;
import co.edu.unimagdalena.busesreservationandparcels.domain.enums.SyncStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "offline_sales")
public class OfflineSale {
    @Id
    @Column(name = "offline_sale_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trip_id", nullable = false)
    private Long tripId;

    @Column(name = "seat_number", nullable = false)
    private String seatNumber;

    @Column(name = "from_stop_id", nullable = false)
    private Long fromStopId;

    @Column(name = "to_stop_id", nullable = false)
    private Long toStopId;

    @Column(name = "passenger_id_number", unique = true, nullable = false)
    private String passengerIdNumber;

    @Column(name = "passenger_name")
    private String passengerName;

    @Column(name = "passenger_phone", unique = true)
    private String passengerPhone;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private SyncStatus status = SyncStatus.PENDING;

    @Column(name = "sync_attempts", nullable = false)
    @Builder.Default
    private Integer syncAttempts = 0;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "synced_at", updatable = false)
    private OffsetDateTime syncedAt;

    @Column(name = "ticket_id")
    private Long ticketId;
}
