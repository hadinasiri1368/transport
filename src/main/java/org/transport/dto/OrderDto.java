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
    private Long userId;
    private Long sourceId;
    private Long driverId;
    private Long carId;
    private Long destinationId;
    private Long orderStatusId;
    private Long carPropertyId;
    private String orderDate;
    private String loadingDate;
    private String loadingTime;
    private String description;
    private Long price;
    private Long minPrice;
    private Boolean onlyMyCompanyDriver;
    private List<OrderDetailDto> orderDetails;
    private List<OrderImage> orderImages;
    private String senderFirstNameAndFamily;
    private String senderMobileNumber;
    private String senderAddress;
    private Integer senderUnit;
    private String senderPostalCode;
    private Float senderLatitude;
    private Float senderLongitude;
    private String receiverFirstNameAndfamily;
    private String receiverMobileNumber;
    private String receiverAddress;
    private Integer receiverUnit;
    private String receiverPostalCode;
    private Float receiverLatitude;
    private Float receiverLongitude;
    private Float distance;
}
