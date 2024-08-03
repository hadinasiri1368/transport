package org.transport.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.transport.common.CommonUtils;
import org.transport.dto.DetailLedgerDto;
import org.transport.model.DetailLedger;
import org.transport.service.GenericService;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class DetailLedgerAPI {
    @Autowired
    private GenericService<DetailLedger> service;

    @PostMapping(path = "/transport/detailLedger/add")
    public Long addDetailLedger(@RequestBody DetailLedgerDto detailLedgerDto, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request), uuid);
        DetailLedger detailLedger = new DetailLedger();
        detailLedger.setId(detailLedgerDto.getId());
        detailLedger.setName(detailLedgerDto.getName());
        detailLedger.setNumber(detailLedgerDto.getNumber());
        detailLedger.setUserId(detailLedgerDto.getUserId());
        service.insert(detailLedger, userId);
        return detailLedger.getId();
    }

    @PutMapping(path = "/transport/detailLedger/edit")
    public Long editDetailLedger(@RequestBody DetailLedgerDto detailLedgerDto, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request), uuid);
        DetailLedger detailLedger = new DetailLedger();
        detailLedger.setId(detailLedgerDto.getId());
        detailLedger.setName(detailLedgerDto.getName());
        detailLedger.setNumber(detailLedgerDto.getNumber());
        detailLedger.setUserId(detailLedgerDto.getUserId());
        service.update(detailLedger, userId, DetailLedger.class);
        return detailLedger.getId();
    }

    @DeleteMapping(path = "/transport/detailLedger/remove/{id}")
    public Long removeDetailLedger(@PathVariable Long id) {
        return (long) service.delete(id, DetailLedger.class);
    }

    @GetMapping(path = "/transport/detailLedger/{id}")
    public DetailLedger getDetailLedger(@PathVariable Long id) {
        return service.findOne(DetailLedger.class, id);
    }

    @GetMapping(path = "/transport/detailLedger")
    public Page<DetailLedger> listDetailLedger(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        return service.findAll(DetailLedger.class, page, size);
    }

}
