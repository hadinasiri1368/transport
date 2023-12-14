package org.transport.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "driver", schema = "tpt")
@Entity(name = "carGroup")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Driver extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
