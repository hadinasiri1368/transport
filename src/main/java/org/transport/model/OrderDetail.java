package org.transport.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "[order_detail]", schema = "tpt")
@Entity(name = "orderDetail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_order_id")
    @JsonBackReference
    private Order order;
    @Column(name = "f_base_info_good_id")
    private Long baseInfoGoodId;
    @Column(name = "f_packing_type_id")
    private Long packingTypeId;
    @Column(name = "f_loading_type_id")
    private Long loadingTypeId;
    @Column(name = "f_car_group_id")
    private Long carGroupId;
    @Column(name = "f_car_capacity_id")
    private Long carCapacityId;
    @Column(columnDefinition = "FLOAT")
    private Float weight;
    @Column(columnDefinition = "INT",name = "pakage_count")
    private Integer pakageCount;
    @Column(columnDefinition = "BIGINT",name = "value_price")
    private Long valuePrice;
}
