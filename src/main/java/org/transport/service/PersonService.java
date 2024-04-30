package org.transport.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private JPA<Person, Long> personJPA;


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
            throw new RuntimeException("id.not.found");
        if (CommonUtils.isNull(findOne(person.getId())))
            throw new RuntimeException("id.not.found");
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
        int returnValue = entityManager.createQuery("delete  person o where o.id=:id").setParameter("id", id).executeUpdate();
        if (returnValue == 0) {
            throw new RuntimeException("id.not.found");
        }
        return returnValue;
    }
    public Person findOne(Long id) {
        return personJPA.findOne(Person.class, id);
    }
    public List<Person> findAll() {
        return personJPA.findAll(Person.class);
    }
    public List<Person> findAll(List<UserDto> userDtos) {
        List<Long> personIds = userDtos.stream().map(entity -> entity.getPersonId()).collect(Collectors.toList());
        String hql = "select p from person p where p.id in (:personIds) ";
        Query query = entityManager.createQuery(hql);
        Map<String, Object> param = new HashMap<>();
        param.put("personIds", personIds);
        personJPA.listByQuery(query, param);
        return personJPA.findAll(Person.class);
    }

}

