package org.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.transport.dto.Response.PersonDto;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CarDto {
    private Long id;
    private PersonDto person;
    private Long fuelTypeId;
    private Long carGroupId;
    private Long carCapacityId;
    private PlaqueDto plaque;
    private Long fleetTypeId;
    private String VIN;
    private String chassieNumber;
    private String engineNumber;
    private String PIN;
    private String PAN;
    private String trackingCode;

}
