package org.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ParametersDto {
    private Long id;
    private String paramName;
    private String paramCode;
    private Long paramTypeId;
    private Long paramCategoryId;
    private Long companyId;
    private String value;
}
