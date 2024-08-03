package org.transport.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.transport.common.CommonUtils;
import org.transport.model.GeneralLedger;
import org.transport.service.GenericService;


@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class GeneralLedgerAPI {
    @Autowired
    private GenericService<GeneralLedger> service;

    @PostMapping(path = "/transport/generalLedger/add")
    public Long addGeneralLedger(@RequestBody GeneralLedger generalLedger, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request), uuid);
        service.insert(generalLedger, userId);
        return generalLedger.getId();
    }

    @PutMapping(path = "/transport/generalLedger/edit")
    public Long editGeneralLedger(@RequestBody GeneralLedger generalLedger, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request), uuid);
        service.update(generalLedger, userId, GeneralLedger.class);
        return generalLedger.getId();
    }

    @DeleteMapping(path = "/transport/generalLedger/remove/{id}")
    public Long removeGeneralLedger(@PathVariable Long id) {
        return (long) service.delete(id, GeneralLedger.class);
    }

    @GetMapping(path = "/transport/generalLedger/{id}")
    public GeneralLedger getGeneralLedger(@PathVariable Long id) {
        return service.findOne(GeneralLedger.class, id);
    }

    @GetMapping(path = "/transport/generalLedger")
    public Page<GeneralLedger> listGeneralLedger(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        return service.findAll(GeneralLedger.class, page, size);
    }
}
