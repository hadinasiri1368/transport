package org.transport.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.transport.common.CommonUtils;
import org.transport.model.Car;
import org.transport.model.Driver;
import org.transport.model.Person;
import org.transport.repository.JPA;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DriverService {

    @Value("${PageRequest.page}")
    private Integer page;
    @Value("${PageRequest.size}")
    private Integer size;

    @PersistenceContext
    private EntityManager entityManager;

    private final JPA<Driver, Long> jpaDriver;

    private final PersonService personService;

    public DriverService(JPA<Driver, Long> jpaDriver, PersonService personService) {
        this.jpaDriver = jpaDriver;
        this.personService = personService;
    }

    @Transactional
    public void insert(Driver driver, Long userId) throws Exception {
        driver.setId(null);
        driver.setInsertedUserId(userId);
        driver.setInsertedDateTime(new Date());
        jpaDriver.save(driver);
    }

    @Transactional
    public void update(Driver driver, Long userId) throws Exception {
        if (CommonUtils.isNull(driver.getId()))
            throw new RuntimeException("3005");
        if (CommonUtils.isNull(findOne(Driver.class, driver.getId())))
            throw new RuntimeException("3005");
        driver.setUpdatedUserId(userId);
        driver.setUpdatedDateTime(new Date());
        jpaDriver.update(driver);
    }

    @Transactional
    public void delete(Driver driver) {
        jpaDriver.remove(driver);
    }

    @Transactional
    public int delete(Long id) {
        Query query = entityManager.createQuery("delete from driver where id = :id");
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        return jpaDriver.executeUpdate(query, param);
    }

    public Driver findOne(Class<Driver> aClass, Long id) {
        return jpaDriver.findOne(aClass, id);
    }

    public List<Driver> findAll(Class<Driver> aClass) {
        return jpaDriver.findAll(aClass);
    }

    public List<Driver> findAll() {
        return jpaDriver.findAll(Driver.class);
    }
}
