package org.transport.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.transport.common.CommonUtils;
import org.transport.common.MapperUtil;
import org.transport.dto.VoucherDetailDto;
import org.transport.dto.VoucherDto;
import org.transport.model.Voucher;
import org.transport.model.VoucherDetail;
import org.transport.service.VoucherService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class VoucherAPI {
    @Autowired
    private VoucherService service;

    @PostMapping(path = "/transport/voucher/add")
    public Long addVoucher(@RequestBody VoucherDto voucherDto, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request), uuid);
        Voucher voucher = new Voucher();
        List<VoucherDetail> voucherDetails = MapperUtil.mapToVoucherDetail(voucherDto.getVoucherDetails());
        voucher.setId(voucherDto.getId());
        voucher.setVoucherDate(voucherDto.getVoucherDate());
        voucher.setDescription(voucherDto.getDescription());
        service.insert(voucher, voucherDetails, userId);
        return voucher.getId();
    }

    @PutMapping(path = "/transport/voucher/edit")
    public Long editVoucher(@RequestBody Voucher voucher, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request), uuid);
        service.update(voucher, userId);
        return voucher.getId();
    }

    @PutMapping(path = "/transport/voucherDetail/edit")
    public Long editVoucherDetail(@RequestBody VoucherDetailDto voucherDetailDto, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request), uuid);
        VoucherDetail voucherDetail = MapperUtil.mapToVoucherDetail(voucherDetailDto);
        service.updateVoucherDetail(voucherDetail, userId);
        return voucherDetail.getId();
    }

    @PutMapping(path = "/transport/voucherDetailList/edit")
    public Long editVoucherDetailList(@RequestBody List<VoucherDetailDto> voucherDetailDtos, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        int voucherCount = voucherDetailDtos.stream().collect(Collectors.groupingBy(a -> a.getVoucherId())).size();
        if (voucherCount > 1)
            throw new RuntimeException("2027");
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request), uuid);
        List<VoucherDetail> voucherDetails = MapperUtil.mapToVoucherDetail(voucherDetailDtos);
        service.updateVoucherDetail(voucherDetailDtos.get(0).getVoucherId(), voucherDetails, userId);
        return voucherDetailDtos.get(0).getVoucherId();
    }

    @DeleteMapping(path = "/transport/voucherDetail/remove")
    public Long removeVoucherDetail(@RequestBody List<VoucherDetailDto> voucherDetailDtos) throws Exception {
        List<VoucherDetail> voucherDetails = MapperUtil.mapToVoucherDetail(voucherDetailDtos);
        service.deleteVoucherDetail(voucherDetailDtos.get(0).getVoucherId(), voucherDetails);
        return voucherDetailDtos.get(0).getVoucherId();
    }

    @DeleteMapping(path = "/transport/voucher/remove/{id}")
    public Long removeVoucher(@PathVariable Long id) {
        service.delete(id);
        return id;
    }

    @GetMapping(path = "/transport/voucher/{id}")
    public Voucher getVoucher(@PathVariable Long id) {
        return service.findOne(id);
    }

    @GetMapping(path = "/transport/voucher")
    public Page<Voucher> listVoucher(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, HttpServletRequest request) {
        return service.findAll(Voucher.class, page, size);
    }
}
