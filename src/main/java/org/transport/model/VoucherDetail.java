package org.transport.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "[voucher_detail]", schema = "tpt")
@Entity(name = "voucherDetail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoucherDetail extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "f_voucher_id")
    private Voucher voucher;
    @ManyToOne
    @JoinColumn(name = "f_subsidiary_ledger_id")
    private SubsidiaryLedger subsidiaryLedger;
    @ManyToOne
    @JoinColumn(name = "f_detail_ledger_id")
    private DetailLedger detailLedger;
    @Column(columnDefinition = "BIGINT" ,name = "debit_amount")
    private Long debitAmount;
    @Column(columnDefinition = "BIGINT" ,name = "credit_amount")
    private Long creditAmount;
}
