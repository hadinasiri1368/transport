package org.transport.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.transport.common.CommonUtils;
import org.transport.model.Voucher;
import org.transport.service.GenericService;

import java.util.List;

@RestController
public class VoucherAPI {
    @Autowired
    private GenericService<Voucher> service;

    @PostMapping(path = "/api/voucher/add")
    public Long addVoucher(@RequestBody Voucher voucher, HttpServletRequest request) {
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request));
        service.update(voucher, userId);
        return voucher.getId();
    }

    @PostMapping(path = "/api/voucher/edit")
    public Long editVoucher(@RequestBody Voucher voucher, HttpServletRequest request) {
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request));
        service.update(voucher, userId);
        return voucher.getId();
    }

    @PostMapping(path = "/api/voucher/remove/{id}")
    public Long removeVoucher(@PathVariable Long id) {
        service.delete(id, Voucher.class);
        return id;
    }

    @GetMapping(path = "/api/voucher/{id}")
    public Voucher getVoucher(@PathVariable Long id) {
        return service.findOne(Voucher.class, id);
    }

    @GetMapping(path = "/api/voucher")
    public List<Voucher> listVoucher() {
        return service.findAll(Voucher.class);
    }
}
