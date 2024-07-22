package org.transport.dto;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.transport.model.OrderImage;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private Long userId;
    private Long carTypeId;
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
    private String orderDate;
    private String loadingDate;
    private String loadingTime;
    private String description;
    private Long price;
    private Long minPrice;
    private Float carGroupFactor;
    private Float loadingTypeFactor;
    private Float tonKilometersFactor;
    private Float durationFactor;
    private Boolean onlyMyCompanyDriver;
    private List<OrderDetailDto> orderDetails;
    private List<OrderImage> orderImages;
}
