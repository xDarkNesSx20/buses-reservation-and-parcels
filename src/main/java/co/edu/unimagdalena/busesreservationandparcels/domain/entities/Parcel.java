package co.edu.unimagdalena.busesreservationandparcels.domain.entities;

import co.edu.unimagdalena.busesreservationandparcels.domain.enums.ParcelStatus;
import jakarta.persistence.*;
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
@Table(name = "parcels")
public class Parcel {
    @Id
    @Column(name = "parcel_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false, length = 16)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_stop_id", nullable = false)
    private Stop fromStop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_stop_id", nullable = false)
    private Stop toStop;

    @Column(nullable = false, precision =  8, scale = 2)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private AppUser sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private AppUser receiver;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ParcelStatus status = ParcelStatus.CREATED;

    @Column(name = "delivery_otp", length = 8, unique = true)
    private String deliveryOTP;
}
