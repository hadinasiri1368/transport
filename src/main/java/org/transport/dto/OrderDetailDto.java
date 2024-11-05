package org.transport.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class OrderDetailDto {
    private Long id;
    private Long orderId;
    private Long baseInfoGoodId;
    private String baseInfoGoodName;
    private Long packingTypeId;
    private String packingTypeName;
    private Long loadingTypeId;
    private String loadingTypeName;
    private Float weight;
    private Integer packageCount;
    private Long valuePrice;
}
