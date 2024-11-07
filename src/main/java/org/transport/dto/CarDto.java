package org.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.transport.model.Person;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CarDto {
    private Long id;
    private List <DriverDto> driver;
    private List<Person> person;
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
