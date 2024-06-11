package org.transport.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "[order]", schema = "tpt")
@Entity(name = "order")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "f_user_id")
    private Long userId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "f_driver_id")
    private Driver driver;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "f_car_id")
    private Car car;
    @Column(name = "f_order_status_id")
    private Long orderStatusId;
    @Column(name = "f_car_type_id")
    private Long carTypeId;
    @Column(columnDefinition = "NVARCHAR(10)", name = "order_date")
    private String orderDate;
    @Column(columnDefinition = "NVARCHAR(10)", name = "loading_date")
    private String loadingDate;
    @Column(columnDefinition = "NVARCHAR(5)", name = "loading_time")
    private String loadingTime;
    @Column(columnDefinition = "NVARCHAR(500)")
    private String description;
    @Column(columnDefinition = "BIGINT")
    private Long price;
    @Column(columnDefinition = "BIGINT", name = "min_price")
    private Long minPrice;
    @Column(columnDefinition = "BIT", name = "only_my_company_driver")
    private Boolean onlyMyCompanyDriver;
    @OneToMany(fetch = FetchType.LAZY, targetEntity = OrderDetail.class)
    @JoinColumn(name = "f_order_id")
    @JsonManagedReference
    private List<OrderDetail> orderDetails;
    @OneToMany(fetch = FetchType.LAZY, targetEntity = OrderImage.class)
    @JoinColumn(name = "f_order_id")
    @JsonManagedReference
    private List<OrderImage> orderImages;
    @Column(columnDefinition = "NVARCHAR(100)", name = "sender_first_name_family")
    private String senderFirstNameAndFamily;
    @Column(columnDefinition = "NVARCHAR(50)", name = "sender_mobile_number")
    private String senderMobileNumber;
    @Column(columnDefinition = "NVARCHAR(100)", name = "sender_address")
    private String senderAddress;
    @Column(columnDefinition = "INT", name = "sender_unit")
    private Integer senderUnit;
    @Column(columnDefinition = "NVARCHAR(50)", name = "sender_postal_code")
    private String senderPostalCode;
    @Column(columnDefinition = "FLOAT", name = "sender_latitude")
    private Float senderLatitude;
    @Column(columnDefinition = "FLOAT", name = "sender_longitude")
    private Float senderLongitude;
    @Column(columnDefinition = "NVARCHAR(100)", name = "receiver_first_name_family")
    private String receiverFirstNameAndfamily;
    @Column(columnDefinition = "NVARCHAR(50)", name = "receiver_mobile_number")
    private String receiverMobileNumber;
    @Column(columnDefinition = "NVARCHAR(100)", name = "receiver_address")
    private String receiverAddress;
    @Column(columnDefinition = "INT", name = "receiver_unit")
    private Integer receiverUnit;
    @Column(columnDefinition = "NVARCHAR(50)", name = "receiver_postal_code")
    private String receiverPostalCode;
    @Column(columnDefinition = "FLOAT", name = "receiver_latitude")
    private Float receiverLatitude;
    @Column(columnDefinition = "FLOAT", name = "receiver_longitude")
    private Float receiverLongitude;
    @Column(columnDefinition = "FLOAT", name = "distance")
    private Float distance;
}
