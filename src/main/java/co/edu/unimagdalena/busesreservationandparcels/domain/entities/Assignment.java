package co.edu.unimagdalena.busesreservationandparcels.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "assignments")
public class Assignment {
    @Id
    @Column(name = "assignment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private AppUser driver;

    @ManyToOne
    @JoinColumn(name = "dispatcher_id", nullable = false)
    private AppUser dispatcher;

    @CreationTimestamp
    @Column(name = "assigned_at", nullable = false, updatable = false)
    private OffsetDateTime assignedAt;

    @Column(name = "check_list_Ok")
    @Builder.Default
    private Boolean checkListOk = false;
}
