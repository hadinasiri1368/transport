package org.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DriverDto {
    private Long id;
    private String trackingCode;
    private Long personId;
    private Long driverLicenseTypeId;
    private String driverLicenseIssueDate;
    private Integer driverLicenseValidDuration;
}
