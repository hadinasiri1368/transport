package org.transport.api;

import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.transport.common.CommonUtils;
import org.transport.common.MapperUtil;
import org.transport.common.ObjectMapperUtils;
import org.transport.dto.VoucherDetailDto;
import org.transport.dto.VoucherDto;
import org.transport.model.Voucher;
import org.transport.model.VoucherDetail;
import org.transport.service.GenericService;
import org.transport.service.VoucherService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class VoucherAPI {
    @Autowired
    private VoucherService service;

    @PostMapping(path = "/api/voucher/add")
    public Long addVoucher(@RequestBody VoucherDto voucherDto, HttpServletRequest request) {
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request));
        Voucher voucher = new Voucher();
        List<VoucherDetail> voucherDetails = MapperUtil.mapToVoucherDetail(voucherDto.getVoucherDetails());
        voucher.setId(voucherDto.getId());
        voucher.setVoucherDate(voucherDto.getVoucherDate());
        voucher.setDescription(voucherDto.getDescription());
        service.insert(voucher, voucherDetails, userId);
        return voucher.getId();
    }

    @PostMapping(path = "/api/voucher/edit")
    public Long editVoucher(@RequestBody Voucher voucher, HttpServletRequest request) {
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request));
        service.update(voucher, userId);
        return voucher.getId();
    }

    @PostMapping(path = "/api/voucherDetail/edit")
    public Long editVoucherDetail(@RequestBody VoucherDetailDto voucherDetailDto, HttpServletRequest request) {
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request));
        VoucherDetail voucherDetail = MapperUtil.mapToVoucherDetail(voucherDetailDto);
        service.updateVoucherDetail(voucherDetail, userId);
        return voucherDetail.getId();
    }

    @PostMapping(path = "/api/voucherDetailList/edit")
    public Long editVoucherDetailList(@RequestBody List<VoucherDetailDto> voucherDetailDtos, HttpServletRequest request) {
        int voucherCount = voucherDetailDtos.stream().collect(Collectors.groupingBy(a -> a.getVoucherId())).size();
        if (voucherCount > 1)
            throw new RuntimeException("voucherId must be the same");
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request));
        List<VoucherDetail> voucherDetails = MapperUtil.mapToVoucherDetail(voucherDetailDtos);
        service.updateVoucherDetail(voucherDetailDtos.get(0).getVoucherId(), voucherDetails, userId);
        return voucherDetailDtos.get(0).getVoucherId();
    }

    @PostMapping(path = "/api/voucher/remove/{id}")
    public Long removeVoucher(@PathVariable Long id) {
        service.delete(id);
        return id;
    }

    @GetMapping(path = "/api/voucher/{id}")
    public Voucher getVoucher(@PathVariable Long id) {
        return service.findOne(id);
    }

    @GetMapping(path = "/api/voucher")
    public List<Voucher> listVoucher() {
        return service.findAll(Voucher.class);
    }
}
