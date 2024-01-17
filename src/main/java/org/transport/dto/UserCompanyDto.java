package org.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserCompanyDto {
    private Long id;
    private Long companyId;
    private Long userId;
}
