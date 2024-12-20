package org.transport.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoadingTypeDto {
    private Long id;
    private String code;
    private String name;
}
