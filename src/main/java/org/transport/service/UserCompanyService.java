package org.transport.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.transport.common.CommonUtils;
import org.transport.common.Const;
import org.transport.dto.RoleDto;
import org.transport.dto.UserDto;
import org.transport.model.*;
import org.transport.repository.JPA;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserCompanyService {
    @Autowired
    private JPA<UserCompany, Long> UserCompanyJPA;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private JPA<Person, Long> personJPA;
    @Autowired
    private  AuthenticationServiceProxy authenticationServiceProxy;

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

    public List<UserCompany> findByUserId(Long userId) {
        String hql = "select u from userCompany u where u.userId = (:userId)";
        Query query = entityManager.createQuery(hql);
        Map<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        List<UserCompany> userCompanies = (List<UserCompany>) UserCompanyJPA.listByQuery(query, param);
        return userCompanies;
    }

    public List<Long> getUserColleague(Long userId) {
        String hql = "select uc.userId from userCompany uc where uc.company.id in (select u.company.id from userCompany u where u.userId = (:userId))";
        Query query = entityManager.createQuery(hql);
        Map<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        List<Long> users = (List<Long>) UserCompanyJPA.listByQuery(query, param);
        return users;
    }

    public List<UserCompany> findAll(Class<UserCompany> aClass) {
        return UserCompanyJPA.findAll(aClass);
    }

    private void checkData(UserCompany userCompany, String token) throws Exception {
        List<RoleDto> roleDtos = new ArrayList<>();
        roleDtos = authenticationServiceProxy.listRole(token, userCompany.getUserId());
        if (roleDtos.stream().filter(a -> a.getId().equals(Const.ROLE_STAFF)).count() <= 0)
            throw new RuntimeException("user.role.is.not.found");
        Person company = personJPA.findOne(Person.class, userCompany.getCompany().getId());
        if (CommonUtils.isNull(company))
            throw new RuntimeException("company.is.null");
        if (!company.getIsCompany())
            throw new RuntimeException("company.not.found");
    }

}
