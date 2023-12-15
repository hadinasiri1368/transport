package org.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PlaqueDto {
    private Long id;
    private boolean isFreeZone;
    private String leftPlaqueTag;
    private Long plaqueTagPersianPartId;
    private String middlePlaqueTag;
    private String rightPlaqueTag;
    private String leftPlaqueFreeZoneTag;
    private String rightPlaqueFreeZoneTag;

}
