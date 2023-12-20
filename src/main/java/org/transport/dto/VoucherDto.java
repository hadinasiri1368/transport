package org.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.transport.model.VoucherDetail;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class VoucherDto {
    private Long id;
    private String description;
    private String voucherDate;
    private List<VoucherDetailDto> voucherDetails;
}
