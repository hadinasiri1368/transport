package org.transport.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.transport.common.CommonUtils;
import org.transport.constant.Const;
import org.transport.dto.RoleDto;
import org.transport.model.*;
import org.transport.repository.JPA;

import java.util.*;

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
    private AuthenticationServiceProxy authenticationServiceProxy;

    @Value("${PageRequest.page}")
    private Integer page;
    @Value("${PageRequest.size}")
    private Integer size;

    @Transactional
    public void insert(UserCompany userCompany, Long userId, String token, String uuid) throws Exception {
        checkData(userCompany, token, uuid);
        userCompany.setId(null);
        userCompany.setInsertedUserId(userId);
        userCompany.setInsertedDateTime(new Date());
        UserCompanyJPA.save(userCompany);
    }

    @Transactional
    public void update(UserCompany userCompany, Long userId, String token, String uuid) throws Exception {
        checkData(userCompany, token, uuid);
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

    private void checkData(UserCompany userCompany, String token, String uuid) throws Exception {
        List<RoleDto> roleDtos = new ArrayList<>();
        roleDtos = authenticationServiceProxy.listRole(token, uuid, userCompany.getUserId());
        if (roleDtos.stream().filter(a -> a.getId().equals(Const.ROLE_STAFF)).count() <= 0)
            throw new RuntimeException("2015");
        Person company = personJPA.findOne(Person.class, userCompany.getCompany().getId());
        if (CommonUtils.isNull(company))
            throw new RuntimeException("2018");
        if (!company.getIsCompany())
            throw new RuntimeException("2019");
    }

    public Page<UserCompany> findAll(Class<UserCompany> aClass, Integer page, Integer size) {
        if (CommonUtils.isNull(page) && CommonUtils.isNull(size)) {
            return UserCompanyJPA.findAllWithPaging(aClass);
        }
        PageRequest pageRequest = PageRequest.of(CommonUtils.isNull(page, this.page), CommonUtils.isNull(size, this.size));
        return UserCompanyJPA.findAllWithPaging(aClass, pageRequest);
    }

}
