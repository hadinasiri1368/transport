package org.transport.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.transport.common.CommonUtils;
import org.transport.model.Person;
import org.transport.service.GenericService;

import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class PersonAPI {
    @Autowired
    private GenericService<Person> service;

    @PostMapping(path = "/api/person/add")
    public Long addPerson(@RequestBody Person person, HttpServletRequest request) throws Exception {
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request));
        service.insert(person, userId);
        return person.getId();
    }

    @PostMapping(path = "/api/person/edit")
    public Long editPerson(@RequestBody Person person, HttpServletRequest request) throws Exception {
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request));
        service.update(person, userId, Person.class);
        return person.getId();
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
