package org.transport.common;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.transport.dto.VoucherDetailDto;
import org.transport.model.DetailLedger;
import org.transport.model.SubsidiaryLedger;
import org.transport.model.Voucher;
import org.transport.model.VoucherDetail;

import java.util.List;
import java.util.stream.Collectors;

public class MapperUtil {
    public static VoucherDetail mapToVoucherDetail (VoucherDetailDto voucherDetailDto){
        VoucherDetail voucherDetail = ObjectMapperUtils.map(voucherDetailDto , VoucherDetail.class);
        voucherDetail.setDebitAmount(CommonUtils.isNull(voucherDetail.getDebitAmount()) ? 0 : voucherDetail.getDebitAmount());
        voucherDetail.setCreditAmount(CommonUtils.isNull(voucherDetail.getCreditAmount()) ? 0 : voucherDetail.getCreditAmount());
        voucherDetail.setSubsidiaryLedger(new SubsidiaryLedger(voucherDetailDto.getSubsidiaryLedgerId(),null,null,null));
        voucherDetail.setDetailLedger(new DetailLedger(voucherDetailDto.getDetailLedgerId(),null,null,null));
        voucherDetail.setVoucher(new Voucher(voucherDetailDto.getVoucherId(),null,null,null));
        return voucherDetail;
    }

    public static List<VoucherDetail> mapToVoucherDetail (List<VoucherDetailDto> voucherDetailDtos){
        return voucherDetailDtos.stream()
                .map(entity -> mapToVoucherDetail(entity))
                .collect(Collectors.toList());
    }

}
