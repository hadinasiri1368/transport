package org.transport.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.transport.common.*;
import org.transport.constant.ChangeRequest;
import org.transport.constant.Const;
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
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JPA<CompanyDriver, Long> CompanyDriverJPA;

    @Autowired
    private JPA<Person, Long> personJPA;

    @Autowired
    private AuthenticationServiceProxy authenticationServiceProxy;

    @Transactional
    public void insert(CompanyDriver companyDriver, Long userId, String token,String uuid) throws Exception {
        checkData(companyDriver, token,uuid);
        companyDriver.setId(null);
        companyDriver.setInsertedUserId(userId);
        companyDriver.setInsertedDateTime(new Date());
        companyDriver.setRequestStatusId(Const.REQUEST_STATUS_PENDING);
        CompanyDriverJPA.save(companyDriver);
    }

    @Transactional
    public void update(CompanyDriver companyDriver, Long userId, String token,String uuid) throws Exception {
        if (!companyDriver.getRequestStatusId().equals(Const.REQUEST_STATUS_PENDING))
            throw new RuntimeException("2017");
        checkData(companyDriver, token,uuid);
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

    private void checkData(CompanyDriver companyDriver, String token, String uuid) throws Exception {
        List<RoleDto> roleDtos = new ArrayList<>();
        roleDtos = authenticationServiceProxy.listRole(token, uuid);
        if (roleDtos.stream().noneMatch(a -> a.getId().equals(Const.ROLE_DRIVER)))
            throw new RuntimeException("2016");
        Person company = personJPA.findOne(Person.class, companyDriver.getCompany().getId());
        if (CommonUtils.isNull(company))
            throw new RuntimeException("2018");
        if (!company.getIsCompany())
            throw new RuntimeException("2019");
        UserDto userDto = authenticationServiceProxy.findPersonUser(token,uuid,company.getId());
        roleDtos = authenticationServiceProxy.listRole(token,uuid, userDto.getId());
        if (roleDtos.stream().noneMatch(a -> a.getId().equals(Const.ROLE_COMPANY))){
            throw new RuntimeException("2028");
        }
    }

    @Transactional
    public void changeRequestDriver(String token, ChangeRequestDto changeRequestDto, String uuid) throws Exception {
        UserDto userDto = ObjectMapperUtils.map(authenticationServiceProxy.getUser(token, uuid), UserDto.class);
        List<RoleDto> roleDtos = new ArrayList<>();
        if (CommonUtils.isNull(userDto))
            throw new RuntimeException("2002");

        CompanyDriver companyDriver = findOne(changeRequestDto.getId());
        if (CommonUtils.isNull(companyDriver))
            throw new RuntimeException("2021");

        if (!companyDriver.getRequestStatusId().equals(Const.REQUEST_STATUS_PENDING))
            throw new RuntimeException("2022");

        if (changeRequestDto.getChangStatus().equals(ChangeRequest.ACCEPT.getValue()))
            companyDriver.setRequestStatusId(Const.REQUEST_STATUS_CONFIRM);

        if (changeRequestDto.getChangStatus().equals(ChangeRequest.REJECT.getValue()))
            companyDriver.setRequestStatusId(Const.REQUEST_STATUS_REJECT);

    }

}
