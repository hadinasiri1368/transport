package org.transport.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.transport.common.CommonUtils;
import org.transport.dto.SubsidiaryLedgerDto;
import org.transport.model.GeneralLedger;
import org.transport.model.SubsidiaryLedger;
import org.transport.service.GenericService;


@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class SubsidiaryLedgerAPI {
    @Autowired
    private GenericService<SubsidiaryLedger> service;

    @PostMapping(path = "/transport/subsidiaryLedger/add")
    public Long addSubsidiaryLedger(@RequestBody SubsidiaryLedgerDto subsidiaryLedgerDto, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request), uuid);
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

    @PutMapping(path = "/transport/subsidiaryLedger/edit")
    public Long editSubsidiaryLedger(@RequestBody SubsidiaryLedgerDto subsidiaryLedgerDto, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request), uuid);
        SubsidiaryLedger subsidiaryLedger = new SubsidiaryLedger();
        subsidiaryLedger.setId(subsidiaryLedgerDto.getId());
        GeneralLedger generalLedger = new GeneralLedger();
        generalLedger.setId(subsidiaryLedgerDto.getGeneralLedgerId());
        subsidiaryLedger.setGeneralLedger(generalLedger);
        subsidiaryLedger.setNumber(subsidiaryLedgerDto.getNumber());
        subsidiaryLedger.setName(subsidiaryLedgerDto.getName());
        service.update(subsidiaryLedger, userId, SubsidiaryLedger.class);
        return subsidiaryLedger.getId();
    }

    @DeleteMapping(path = "/transport/subsidiaryLedger/remove/{id}")
    public Long removeSubsidiaryLedger(@PathVariable Long id) {
        return (long) service.delete(id, SubsidiaryLedger.class);
    }

    @GetMapping(path = "/transport/subsidiaryLedger/{id}")
    public SubsidiaryLedger getSubsidiaryLedger(@PathVariable Long id) {
        return service.findOne(SubsidiaryLedger.class, id);
    }

    @GetMapping(path = "/transport/subsidiaryLedger")
    public Page<SubsidiaryLedger> listSubsidiaryLedger(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        return service.findAll(SubsidiaryLedger.class, page, size);
    }

}
