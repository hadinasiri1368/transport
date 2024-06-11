package org.transport.dto.Response;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class NeshanDistanceDto {
    private String status;
    private List<NeshanRowDto> rows;
    private List<String> origin_addresses;
    private List<String> destination_addresses;
}

