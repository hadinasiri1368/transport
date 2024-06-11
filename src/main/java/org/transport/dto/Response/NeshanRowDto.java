package org.transport.dto.Response;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class NeshanRowDto {
    private List<NeshanElementDto> elements;
}
