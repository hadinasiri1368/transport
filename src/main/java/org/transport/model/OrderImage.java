package org.transport.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "[order_image]", schema = "tpt")
@Entity(name = "orderImage")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="f_order_id")
    private Long orderId;
    @Column(columnDefinition = "IMAGE",name ="pic")
    private byte[] pic;
}
