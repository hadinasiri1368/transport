package org.transport.service;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.transport.common.CommonUtils;
import org.transport.common.Const;
import org.transport.dto.RoleDto;
import org.transport.model.*;
import org.transport.repository.JPA;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserCompanyService {
    @Autowired
    private JPA<UserCompany, Long> UserCompanyJPA;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private JPA<Person, Long> personJPA;

    @Transactional
    public void insert(UserCompany userCompany, Long userId, String token) throws Exception {
        checkData(userCompany, token);
        userCompany.setId(null);
        userCompany.setInsertedUserId(userId);
        userCompany.setInsertedDateTime(new Date());
        UserCompanyJPA.save(userCompany);
    }

    @Transactional
    public void update(UserCompany userCompany, Long userId, String token) throws Exception {
        checkData(userCompany, token);
        userCompany.setId(null);
        userCompany.setInsertedUserId(userId);
        userCompany.setInsertedDateTime(new Date());
        UserCompanyJPA.update(userCompany);
    }

    public int delete(Long id) {
        return entityManager.createQuery("delete from userCompany where userCompany.id=" + id).executeUpdate();
    }

    public UserCompany findOne(Long id) {
        return UserCompanyJPA.findOne(UserCompany.class, id);
    }

    public List<UserCompany> findAll(Class<UserCompany> aClass) {
        return UserCompanyJPA.findAll(aClass);
    }

    private void checkData(UserCompany userCompany, String token) throws Exception {
        List<RoleDto> roleDtos = new ArrayList<>();
        roleDtos = CommonUtils.getUserRole(userCompany.getUserId(), token);
        if (roleDtos.stream().filter(a -> a.getId().equals(Const.ROLE_STAFF)).count() <= 0)
            throw new RuntimeException("user.role.is.not.found");
        Person company = personJPA.findOne(Person.class, userCompany.getCompany().getId());
        if (CommonUtils.isNull(company))
            throw new RuntimeException("company.is.null");
        if (!company.getIsCompany())
            throw new RuntimeException("company.not.found");
    }

}
