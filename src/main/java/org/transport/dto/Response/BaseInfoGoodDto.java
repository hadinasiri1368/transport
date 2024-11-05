package org.transport.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseInfoGoodDto {
    private Long id;
    private String code;
    private String name;
}
