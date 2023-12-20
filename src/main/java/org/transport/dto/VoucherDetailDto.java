package org.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class VoucherDetailDto {
    private Long id;
    private Long voucherId;
    private Long subsidiaryLedgerId;
    private Long detailLedgerId;
    private Long debitAmount;
    private Long creditAmount;
}
