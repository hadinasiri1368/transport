package org.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CarGroupDto {
    private Long id;
    private Long carCapacityId;
    private Long carTypeId;
    private Long companyId;
    private String factorValue;
}
