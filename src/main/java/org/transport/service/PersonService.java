package org.transport.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.transport.common.CommonUtils;
import org.transport.dto.UserDto;
import org.transport.model.*;
import org.transport.repository.JPA;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class PersonService {
    @PersistenceContext
    private EntityManager entityManager;

    private final JPA<Person, Long> personJPA;

    private final AuthenticationServiceProxy authenticationServiceProxy;

    @Value("${PageRequest.page}")
    private Integer page;
    @Value("${PageRequest.size}")
    private Integer size;

    public PersonService(AuthenticationServiceProxy authenticationServiceProxy, JPA<Person, Long> personJPA) {
        this.authenticationServiceProxy = authenticationServiceProxy;
        this.personJPA = personJPA;
    }

    @Transactional
    public void insert(Person person, Long userId) throws Exception {
        person.setId(null);
        person.setInsertedUserId(userId);
        person.setInsertedDateTime(new Date());
        personJPA.save(person);
    }

    @Transactional
    public void update(Person person, Long userId) throws Exception {
        if (CommonUtils.isNull(person.getId()))
            throw new RuntimeException("2004");
        if (CommonUtils.isNull(findOne(person.getId())))
            throw new RuntimeException("2004");
        person.setUpdatedUserId(userId);
        person.setUpdatedDateTime(new Date());
        personJPA.update(person);
    }

    @Transactional
    public void delete(Person person) {
        personJPA.remove(person);
    }

    @Transactional
    public int delete(Long id) {
        Query query = entityManager.createQuery("delete from person where id = :id");
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        return personJPA.executeUpdate(query, param);
    }

    public Person findOne(Long id) {
        return personJPA.findOne(Person.class, id);
    }

    public List<Person> findAll() {
        return personJPA.findAll(Person.class);
    }

    public List<Person> findAll(List<UserDto> userDtos) {
        List<Long> personIds = userDtos.stream().map(UserDto::getPersonId).collect(Collectors.toList());
        String hql = "select p from person p where p.id in (:personIds) ";
        Query query = entityManager.createQuery(hql);
        Map<String, Object> param = new HashMap<>();
        param.put("personIds", personIds);
        return personJPA.listByQuery(query, param);
    }

    public Page<Person> findAll(Class<Person> aClass, Integer page, Integer size) {
        if (CommonUtils.isNull(page) && CommonUtils.isNull(size)) {
            return personJPA.findAllWithPaging(aClass);
        }
        PageRequest pageRequest = PageRequest.of(CommonUtils.isNull(page, this.page), CommonUtils.isNull(size, this.size));
        return personJPA.findAllWithPaging(aClass, pageRequest);
    }

    public List<Person> findPersonsRole(Long roleId, String token, String uuid) {
        List<UserDto> userDtos = authenticationServiceProxy.findAllUserRole(token, uuid, roleId);
        return findAll(userDtos);
    }

}

