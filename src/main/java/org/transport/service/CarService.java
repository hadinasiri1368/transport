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
import org.transport.dto.CarDto;
import org.transport.dto.DriverDto;
import org.transport.dto.Response.PlaqueTagPersianPartDto;
import org.transport.model.Car;
import org.transport.model.Driver;
import org.transport.model.Person;
import org.transport.model.Plaque;
import org.transport.repository.JPA;

import java.util.*;

@Service
@Slf4j
public class CarService {
    @Value("${PageRequest.page}")
    private Integer page;
    @Value("${PageRequest.size}")
    private Integer size;

    @PersistenceContext
    private EntityManager entityManager;

    private final JPA<Car, Long> jpaCar;

    private final PersonService personService;

    private final BasicDataServiceProxy basicDataServiceProxy;

    public CarService(JPA<Car, Long> jpaCar, PlaqueService plaqueService, PersonService personService, BasicDataServiceProxy basicDataServiceProxy) {
        this.jpaCar = jpaCar;
        this.personService = personService;
        this.basicDataServiceProxy = basicDataServiceProxy;
    }

    @Transactional
    public void insert(Car car, Long userId) throws Exception {
        car.setId(null);
        car.setInsertedUserId(userId);
        car.setInsertedDateTime(new Date());
        jpaCar.save(car);
    }

    @Transactional
    public void update(Car car, Long userId) throws Exception {
        if (CommonUtils.isNull(car.getId()))
            throw new RuntimeException("3005");
        if (CommonUtils.isNull(findOne(Car.class, car.getId())))
            throw new RuntimeException("3005");
        car.setUpdatedUserId(userId);
        car.setUpdatedDateTime(new Date());
        jpaCar.update(car);
    }

    @Transactional
    public void delete(Car car) {
        jpaCar.remove(car);
    }

    @Transactional
    public int delete(Long id) {
        Query query = entityManager.createQuery("delete from car where id = :id");
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        return jpaCar.executeUpdate(query, param);
    }

    public Car findOne(Class<Car> aClass, Long id) {
        return jpaCar.findOne(aClass, id);
    }

    public List<Car> findAll(Class<Car> aClass) {
        return jpaCar.findAll(aClass);
    }

    public Page<CarDto> findAll(Integer page, Integer size, String token, String uuid) {
        List<Car> cars = findAll(Car.class);
        List<Person> persons = personService.findAll();
        List<CarDto> carDtos = new ArrayList<>();
        List<PlaqueTagPersianPartDto> plaqueTagPersianPartDtos = basicDataServiceProxy.listPlaqueTagPersianPart(token, uuid).getContent();
        CarDto carDto;
        for (Car car : cars) {
            Plaque plaque = car.getPlaque();
            carDto = MapperUtil.mapToCarDto(car, plaque, persons, plaqueTagPersianPartDtos);
            carDtos.add(carDto);
        }
        PageRequest pageRequest = PageRequest.of(CommonUtils.isNull(page, this.page), CommonUtils.isNull(size, this.size));
        return CommonUtils.listPaging(carDtos, pageRequest);
    }


}
