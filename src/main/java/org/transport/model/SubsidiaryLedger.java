package org.transport.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "[subsidiary_ledger]", schema = "tpt")
@Entity(name = "subsidiaryLedger")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubsidiaryLedger extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "INT")
    private int number;
    @Column(columnDefinition = "NVARCHAR(50)")
    private String name;
    @ManyToOne
    @JoinColumn(name = "f_general_ledger_id")
    private GeneralLedger generalLedger;
}
