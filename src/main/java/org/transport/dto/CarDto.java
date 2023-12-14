package org.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CarDto {
    private Long id;
    private Long driverId;
    private Long ownerId;
    private Long fuelTypeId;
    private Long carGroupId;
    private Long carCapacityId;
    private Long plaqueId;
    private Long fleetTypeId;
    private String VIN;
    private String chassieNumber;
    private String engineNumber;
    private String PIN;
    private String PAN;
    private String trackingCode;

}
