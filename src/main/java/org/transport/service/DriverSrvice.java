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
import org.transport.common.MapperUtil;
import org.transport.common.ObjectMapperUtils;
import org.transport.dto.DriverDto;
import org.transport.dto.Response.PersonDto;
import org.transport.model.Car;
import org.transport.model.Driver;
import org.transport.model.Person;
import org.transport.repository.JPA;

import java.util.*;

@Service
@Slf4j
public class DriverSrvice {
    @Value("${PageRequest.page}")
    private Integer page;
    @Value("${PageRequest.size}")
    private Integer size;

    @PersistenceContext
    private EntityManager entityManager;

    private final JPA<Driver, Long> jpaDriver;

    private final BasicDataServiceProxy basicDataServiceProxy;

    public DriverSrvice(JPA<Driver, Long> jpaDriver, BasicDataServiceProxy basicDataServiceProxy) {
        this.jpaDriver = jpaDriver;
        this.basicDataServiceProxy = basicDataServiceProxy;
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

    public Page<DriverDto> findAll(Integer page, Integer size, String token, String uuid) {
        List<Driver> drivers = jpaDriver.findAll(Driver.class);
        List<DriverDto> driverDtos = new ArrayList<>();
        for (Driver driver : drivers) {
            DriverDto driverDto = new DriverDto();
            Person person = driver.getPerson();
            MapperUtil.mapToDriverDto(driver);
            driverDto.setPerson(ObjectMapperUtils.map(person, PersonDto.class));
            driverDtos.add(driverDto);
        }
        PageRequest pageRequest = PageRequest.of(CommonUtils.isNull(page, this.page), CommonUtils.isNull(size, this.size));
        return CommonUtils.listPaging(driverDtos, pageRequest);
    }
}
