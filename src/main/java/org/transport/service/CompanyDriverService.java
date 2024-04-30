package org.transport.service;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.transport.common.ChangeRequest;
import org.transport.common.CommonUtils;
import org.transport.common.Const;
import org.transport.dto.ChangeRequestDto;
import org.transport.dto.RoleDto;
import org.transport.dto.UserDto;
import org.transport.model.CompanyDriver;
import org.transport.model.Person;
import org.transport.repository.JPA;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CompanyDriverService {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private JPA<CompanyDriver, Long> CompanyDriverJPA;

    @Autowired
    private JPA<Person, Long> personJPA;

    @Transactional
    public void insert(CompanyDriver companyDriver, Long userId, String token) throws Exception {
        checkData(companyDriver, token);
        companyDriver.setId(null);
        companyDriver.setInsertedUserId(userId);
        companyDriver.setInsertedDateTime(new Date());
        companyDriver.setRequestStatusId(Const.REQUEST_STATUS_PENDING);
        CompanyDriverJPA.save(companyDriver);
    }

    @Transactional
    public void update(CompanyDriver companyDriver, Long userId, String token) throws Exception {
        if (!companyDriver.getRequestStatusId().equals(Const.REQUEST_STATUS_PENDING))
            throw new RuntimeException("request.status.not.draft");
        checkData(companyDriver, token);
        companyDriver.setId(null);
        companyDriver.setInsertedUserId(userId);
        companyDriver.setInsertedDateTime(new Date());
        CompanyDriverJPA.update(companyDriver);
    }

    public int delete(Long id) {
        return entityManager.createQuery("delete from companyDriver where companyDriver.id=" + id).executeUpdate();
    }

    public CompanyDriver findOne(Long id) {
        return CompanyDriverJPA.findOne(CompanyDriver.class, id);
    }

    public List<CompanyDriver> findAll(Class<CompanyDriver> aClass) {
        return CompanyDriverJPA.findAll(aClass);
    }

    private void checkData(CompanyDriver companyDriver, String token) throws Exception {
        List<RoleDto> roleDtos = new ArrayList<>();
        roleDtos = CommonUtils.getUserRole(token);
        if (roleDtos.stream().filter(a -> a.getId().equals(Const.ROLE_DRIVER)).count() <= 0)
            throw new RuntimeException("user.role.is.not.driver");
        Person company = personJPA.findOne(Person.class, companyDriver.getCompany().getId());
        if (CommonUtils.isNull(company))
            throw new RuntimeException("company.is.null");
        if (!company.getIsCompany())
            throw new RuntimeException("company.not.found");
    }

    @Transactional
    public void changeRequestDriver(String token, ChangeRequestDto changeRequestDto) throws Exception {
        UserDto userDto = CommonUtils.getUser(token);
        List<RoleDto> roleDtos = new ArrayList<>();
        if (CommonUtils.isNull(userDto))
            throw new RuntimeException("can.not.identify.token");

        CompanyDriver companyDriver = findOne(changeRequestDto.getId());
        if (CommonUtils.isNull(companyDriver))
            throw new RuntimeException("request.not.found");

        if (!companyDriver.getRequestStatusId().equals(Const.REQUEST_STATUS_PENDING))
            throw new RuntimeException("request.status.not.in.pending.confirmation");

        if (changeRequestDto.getChangStatus().equals(ChangeRequest.ACCEPT.getValue()))
            companyDriver.setRequestStatusId(Const.REQUEST_STATUS_CONFIRM);

        if (changeRequestDto.getChangStatus().equals(ChangeRequest.REJECT.getValue()))
            companyDriver.setRequestStatusId(Const.REQUEST_STATUS_REJECT);

    }

}
