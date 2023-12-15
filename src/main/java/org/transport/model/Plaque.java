package org.transport.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "plaque", schema = "tpt")
@Entity(name = "plaque")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Plaque extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "BIT", name = "is_free_zone")
    private boolean isFreeZone;
    @Column(columnDefinition = "NVARCHAR(2)", name = "left_plaque_tag")
    private String leftPlaqueTag;
    @Column(columnDefinition = "NVARCHAR(50)", name = "f_plaque_tag_persian_part_id")
    private Long plaqueTagPersianPartId;
    @Column(columnDefinition = "NVARCHAR(3)", name = "middle_plaque_tag")
    private String middlePlaqueTag;
    @Column(columnDefinition = "NVARCHAR(2)", name = "right_plaque_tag")
    private String rightPlaqueTag;
    @Column(columnDefinition = "NVARCHAR(5)", name = "left_plaque_free_zone_tag")
    private String leftPlaqueFreeZoneTag;
    @Column(columnDefinition = "NVARCHAR(2)", name = "right_plaque_free_zone_tag")
    private String rightPlaqueFreeZoneTag;

}
