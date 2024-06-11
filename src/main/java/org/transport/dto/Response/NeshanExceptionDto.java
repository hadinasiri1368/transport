package org.transport.dto.Response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NeshanExceptionDto {
    private String status;
    private Integer code;
    private String message;
}
