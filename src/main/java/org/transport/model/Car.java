package org.transport.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "car", schema = "tpt")
@Entity(name = "car")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Car extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "f_driver_id")
    private Driver driver;
    @ManyToOne
    @JoinColumn(name = "f_owner_id")
    private Person person;
    @Column(columnDefinition = "NVARCHAR(50)", name = "f_fuel_type_id")
    private Long fuelTypeId;
    @Column(columnDefinition = "NVARCHAR(50)", name = "f_car_group_id")
    private Long carGroupId;
    @Column(columnDefinition = "DECIMAL(18,0)", name = "f_car_capacity_id")
    private Long carCapacityId;
    @ManyToOne
    @JoinColumn(name = "f_plaque_id")
    private Plaque plaque;
    @Column(columnDefinition = "DECIMAL(18,0)", name = "f_fleet_type_id")
    private Long fleetTypeId;
    @Column(columnDefinition = "NVARCHAR(50)", name = "VIN")
    private String VIN;
    @Column(columnDefinition = "NVARCHAR(50)", name = "chassie_number")
    private String chassieNumber;
    @Column(columnDefinition = "NVARCHAR(50)", name = "engine_number")
    private String engineNumber;
    @Column(columnDefinition = "NVARCHAR(50)", name = "PIN")
    private String PIN;
    @Column(columnDefinition = "NVARCHAR(50)", name = "PAN")
    private String PAN;
    @Column(columnDefinition = "NVARCHAR(50)", name = "tracking_code")
    private String trackingCode;

}
