package co.edu.unimagdalena.busesreservationandparcels.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "offline_users")
@EqualsAndHashCode(callSuper = true)
public class OfflineUser extends User{
    @Column(nullable = false, unique = true, name = "id_number", length = 10)
    private String idNumber;
}
