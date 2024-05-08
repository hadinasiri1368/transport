package org.transport.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.transport.common.CommonUtils;
import org.transport.model.GeneralLedger;
import org.transport.service.AuthenticationServiceProxy;
import org.transport.service.GenericService;

import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class GeneralLedgerAPI {
    @Autowired
    private GenericService<GeneralLedger> service;
    @Autowired
    private AuthenticationServiceProxy authenticationServiceProxy;

    @PostMapping(path = "/api/generalLedger/add")
    public Long addGeneralLedger(@RequestBody GeneralLedger generalLedger, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.longValue(authenticationServiceProxy.getUserId(CommonUtils.getToken(request), uuid));
        service.insert(generalLedger, userId);
        return generalLedger.getId();
    }

    @PutMapping(path = "/api/generalLedger/edit")
    public Long editGeneralLedger(@RequestBody GeneralLedger generalLedger, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.longValue(authenticationServiceProxy.getUserId(CommonUtils.getToken(request), uuid));
        service.update(generalLedger, userId, GeneralLedger.class);
        return generalLedger.getId();
    }

    @DeleteMapping(path = "/api/generalLedger/remove/{id}")
    public Long removeGeneralLedger(@PathVariable Long id) {
        service.delete(id, GeneralLedger.class);
        return id;
    }

    @GetMapping(path = "/api/generalLedger/{id}")
    public GeneralLedger getGeneralLedger(@PathVariable Long id) {
        return service.findOne(GeneralLedger.class, id);
    }

    @GetMapping(path = "/api/generalLedger")
    public Page<GeneralLedger> listGeneralLedger(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        return service.findAll(GeneralLedger.class,page,size);
    }
}
