package org.transport.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.transport.common.CommonUtils;
import org.transport.dto.SubsidiaryLedgerDto;
import org.transport.model.GeneralLedger;
import org.transport.model.SubsidiaryLedger;
import org.transport.service.GenericService;

import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class SubsidiaryLedgerAPI {
    @Autowired
    private GenericService<SubsidiaryLedger> service;

    @PostMapping(path = "/api/subsidiaryLedger/add")
    public Long addSubsidiaryLedger(@RequestBody SubsidiaryLedgerDto subsidiaryLedgerDto, HttpServletRequest request) {
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request));
        SubsidiaryLedger subsidiaryLedger = new SubsidiaryLedger();
        subsidiaryLedger.setId(subsidiaryLedgerDto.getId());
        GeneralLedger generalLedger = new GeneralLedger();
        generalLedger.setId(subsidiaryLedgerDto.getGeneralLedgerId());
        subsidiaryLedger.setGeneralLedger(generalLedger);
        subsidiaryLedger.setNumber(subsidiaryLedgerDto.getNumber());
        subsidiaryLedger.setName(subsidiaryLedgerDto.getName());
        service.insert(subsidiaryLedger, userId);
        return subsidiaryLedger.getId();
    }

    @PostMapping(path = "/api/subsidiaryLedger/edit")
    public Long editSubsidiaryLedger(@RequestBody SubsidiaryLedger subsidiaryLedger, HttpServletRequest request) {
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request));
        service.update(subsidiaryLedger, userId);
        return subsidiaryLedger.getId();
    }

    @PostMapping(path = "/api/subsidiaryLedger/remove/{id}")
    public Long removeSubsidiaryLedger(@PathVariable Long id) {
        service.delete(id, SubsidiaryLedger.class);
        return id;
    }

    @GetMapping(path = "/api/subsidiaryLedger/{id}")
    public SubsidiaryLedger getSubsidiaryLedger(@PathVariable Long id) {
        return service.findOne(SubsidiaryLedger.class, id);
    }

    @GetMapping(path = "/api/subsidiaryLedger")
    public List<SubsidiaryLedger> listSubsidiaryLedger() {
        return service.findAll(SubsidiaryLedger.class);
    }

}
