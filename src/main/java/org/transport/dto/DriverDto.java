package org.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class DriverDto {
    private Long id;
    private String trackingCode;
    private Long personId;

    private Long driverLicenseTypeId;
    private String driverLicenseIssueDate;
    private Integer driverLicenseValidDuration;
}
