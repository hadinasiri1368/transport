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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_order_id")
    @JsonBackReference
    private Order order;
    @Column(columnDefinition = "IMAGE")
    private byte[] pic;
}
