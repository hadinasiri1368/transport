package org.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CompanyDriverDto {
    private Long id;
    private Long companyId;
    private Long driverId;
    private Long requestStatusId;
}
