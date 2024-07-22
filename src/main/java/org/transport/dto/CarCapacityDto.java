package org.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CarCapacityDto {
    private Long id;
    private String code;
    private String name;
    private  Long maxCapacity;
    private  Long minCapacity;
}
