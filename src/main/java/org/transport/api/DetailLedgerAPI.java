package org.transport.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.transport.common.CommonUtils;
import org.transport.dto.DetailLedgerDto;
import org.transport.model.DetailLedger;
import org.transport.service.AuthenticationServiceProxy;
import org.transport.service.GenericService;

import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class DetailLedgerAPI {
    @Autowired
    private GenericService<DetailLedger> service;
    @Autowired
    private AuthenticationServiceProxy authenticationServiceProxy;
    @PostMapping(path = "/api/detailLedger/add")
    public Long addDetailLedger(@RequestBody DetailLedgerDto detailLedgerDto, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.longValue(authenticationServiceProxy.getUserId(CommonUtils.getToken(request),uuid));
        DetailLedger detailLedger = new DetailLedger();
        detailLedger.setId(detailLedgerDto.getId());
        detailLedger.setName(detailLedgerDto.getName());
        detailLedger.setNumber(detailLedgerDto.getNumber());
        detailLedger.setUserId(detailLedgerDto.getUserId());
        service.insert(detailLedger, userId);
        return detailLedger.getId();
    }

    @PutMapping(path = "/api/detailLedger/edit")
    public Long editDetailLedger(@RequestBody DetailLedgerDto detailLedgerDto, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.longValue(authenticationServiceProxy.getUserId(CommonUtils.getToken(request),uuid));
        DetailLedger detailLedger = new DetailLedger();
        detailLedger.setId(detailLedgerDto.getId());
        detailLedger.setName(detailLedgerDto.getName());
        detailLedger.setNumber(detailLedgerDto.getNumber());
        detailLedger.setUserId(detailLedgerDto.getUserId());
        service.update(detailLedger, userId, DetailLedger.class);
        return detailLedger.getId();
    }

    @DeleteMapping(path = "/api/detailLedger/remove/{id}")
    public Long removeDetailLedger(@PathVariable Long id) {
        service.delete(id, DetailLedger.class);
        return id;
    }

    @GetMapping(path = "/api/detailLedger/{id}")
    public DetailLedger getDetailLedger(@PathVariable Long id) {
        return service.findOne(DetailLedger.class, id);
    }

    @GetMapping(path = "/api/detailLedger")
    public List<DetailLedger> listDetailLedger() {
        return service.findAll(DetailLedger.class);
    }

}
