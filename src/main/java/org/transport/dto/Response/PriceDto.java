package org.transport.dto.Response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PriceDto {
    private Long companyId;
    private Float timeFactor;
    private Float distanceFactor;
    private Float totalLoadingTypeFactor;
    private Float carGroupFactorValue;
    private Float price;
}
