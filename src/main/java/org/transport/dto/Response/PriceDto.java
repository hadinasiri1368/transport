package org.transport.dto.Response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PriceDto {
    private Long companyId;
    private Double timeFactor;
    private Double distanceFactor;
    private Double totalLoadingTypeFactor;
    private Double carGroupFactorValue;
    private Double price;
}
