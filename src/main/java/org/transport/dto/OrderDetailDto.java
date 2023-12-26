package org.transport.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class OrderDetailDto {
    private Long id;
    private Long orderId;
    private Long baseInfoGoodId;
    private Long packingTypeId;
    private Long loadingTypeId;
    private Long carGroupId;
    private Long carCapacityId;
    private Float weight;
    private Integer packageCount;
    private Long valuePrice;
}
