package org.transport.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.transport.common.CommonUtils;
import org.transport.model.Person;
import org.transport.service.GenericService;

import java.util.List;

@RestController
public class PersonAPI {
    @Autowired
    private GenericService<Person> service;

    @PostMapping(path = "/api/person/add")
    public Long addPerson(@RequestBody Person Person, HttpServletRequest request) {
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request));
        service.insert(Person, userId);
        return Person.getId();
    }

    @PostMapping(path = "/api/person/edit")
    public Long editPerson(@RequestBody Person Person, HttpServletRequest request) {
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request));
        service.update(Person, userId);
        return Person.getId();
    }

    @PostMapping(path = "/api/person/remove/{id}")
    public Long removePerson(@PathVariable Long id) {
        service.delete(id, Person.class);
        return id;
    }

    @GetMapping(path = "/api/person/{id}")
    public Person getPerson(@PathVariable Long id) {
        return service.findOne(Person.class, id);
    }

    @GetMapping(path = "/api/person")
    public List<Person> listPerson() {
        return service.findAll(Person.class);
    }
}
