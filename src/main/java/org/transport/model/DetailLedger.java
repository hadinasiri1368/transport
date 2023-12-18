package org.transport.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "[detail_ledger]", schema = "tpt")
@Entity(name = "detailLedger")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailLedger extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "INT")
    private int number;
    @Column(columnDefinition = "NVARCHAR(50)")
    private String name;
    @Column(columnDefinition = "DECIMAL(18,0)",name="f_user_id")
    private Long userId;
}

