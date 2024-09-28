package org.transport.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoadingTypeCompanyDto {
    private Long id;
    private Long loadingTypeId;
    private String name;
    private String code;
    private Long companyId;
    private String companyName;
    private Double factorValue;
}
