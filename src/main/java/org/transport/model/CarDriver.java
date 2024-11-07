package org.transport.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "car_driver", schema = "tpt")
@Entity(name = "carDriver")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarDriver {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "f_car_id")
    private Car car;
    @ManyToOne
    @JoinColumn(name = "f_driver_id")
    private Driver driver;

}
