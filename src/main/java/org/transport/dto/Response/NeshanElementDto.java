package org.transport.dto.Response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class NeshanElementDto {
    private String status;
    private NeshanElement duration;
    private NeshanElement distance;
}
