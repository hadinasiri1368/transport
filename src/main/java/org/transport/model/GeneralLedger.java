package org.transport.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "[general_ledger]", schema = "tpt")
@Entity(name = "generalLedger")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeneralLedger extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "INT")
    private int number;
    @Column(columnDefinition = "NVARCHAR(50)")
    private String name;
}
