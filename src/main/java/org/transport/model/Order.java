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
    @Column(name = "f_source_id")
    private Long sourceId;
    @Column(name = "f_destination_id")
    private Long destinationId;
    @Column(name = "f_order_status_id")
    private Long orderStatusId;
    @Column(name = "f_car_property_id")
    private Long carPropertyId;
    @Column(columnDefinition = "NVARCHAR(10)", name = "order_date")
    private String orderDate;
    @Column(columnDefinition = "NVARCHAR(10)", name = "loading_date")
    private String loadingDate;
    @Column(columnDefinition = "NVARCHAR(5)", name = "loading_time")
    private String loadingTime;
    @Column(columnDefinition = "NVARCHAR(50)", name = "source_postal_code")
    private String sourcePostalCode;
    @Column(columnDefinition = "NVARCHAR(50)", name = "destination_postal_code")
    private String destinationPostalCode;
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
}
