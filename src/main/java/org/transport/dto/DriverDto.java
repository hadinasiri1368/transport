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
public class DriverDto {
    private Long id;
    private String trackingCode;
    private PersonDto person;
    private Long driverLicenseTypeId;
    private String driverLicenseTypeName;
    private String driverLicenseIssueDate;
    private Integer driverLicenseValidDuration;
}
