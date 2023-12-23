package org.transport.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.transport.model.OrderDetail;
import org.transport.model.OrderImage;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class OrderDto {
    private Long id;
    private Long sourceId;
    private Long destinationId;
    private Long orderStatusId;
    private Long carPropertyId;
    private String orderDate;
    private String loadingDate;
    private String loadingTime;
    private String sourcePostalCode;
    private String destinationPostalCode;
    private String description;
    private Long price;
    private Long minPrice;
    private List<OrderDetailDto> orderDetails;
}
