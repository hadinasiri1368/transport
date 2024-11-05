package org.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class PlaqueDto {
    private Long id;
    private Boolean isFreeZone;
    private String leftPlaqueTag;
    private Long plaqueTagPersianPartId;
    private String plaqueTagPersianPartName;
    private String middlePlaqueTag;
    private String rightPlaqueTag;
    private String leftPlaqueFreeZoneTag;
    private String rightPlaqueFreeZoneTag;

}
