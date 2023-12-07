package org.transport.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table(name = "[voucher]", schema = "tpt")
@Entity(name = "voucher")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Voucher extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "NVARCHAR(100)")
    private String description;
    @Column(columnDefinition = "NVARCHAR(10)", name = "voucher_date")
    private String voucherDate;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_voucher_id")
    private List<VoucherDetail> voucherDetails;
}
