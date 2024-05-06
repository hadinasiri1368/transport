package org.transport.api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.transport.common.CommonUtils;
import org.transport.common.ObjectMapperUtils;
import org.transport.dto.UserDto;
import org.transport.dto.UserPersonDto;
import org.transport.model.Person;
import org.transport.model.VoucherDetail;
import org.transport.service.AuthenticationServiceProxy;
import org.transport.service.GenericService;
import org.transport.service.PersonService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class PersonAPI {
    @Autowired
    private PersonService service;
    @Autowired
    private AuthenticationServiceProxy authenticationServiceProxy;

    @PostMapping(path = "/api/person/add")
    public Long addPerson(@RequestBody Person person, HttpServletRequest request) throws Exception {
        Long userId = CommonUtils.longValue(authenticationServiceProxy.getUserId(CommonUtils.getToken(request)));
        service.insert(person, userId);
        return person.getId();
    }

    @PutMapping(path = "/api/person/edit")
    public Long editPerson(@RequestBody Person person, HttpServletRequest request) throws Exception {
        Long userId = CommonUtils.longValue(authenticationServiceProxy.getUserId(CommonUtils.getToken(request)));
        service.update(person, userId);
        return person.getId();
    }

    @DeleteMapping(path = "/api/person/remove/{id}")
    public Long removePerson(@PathVariable Long id) {
        service.delete(id);
        return id;
    }

    @GetMapping(path = "/api/person/{id}")
    public Person getPerson(@PathVariable Long id) {
        return service.findOne(id);
    }

    @GetMapping(path = "/api/person")
    public Page<Person> listPerson(Pageable p) {
        return service.findAll(p);
    }


    @GetMapping (path = "/api/personUser")
    public List<UserPersonDto> listUserPerson(@RequestBody List<UserDto> userDtos) {
        List<Person> personList = service.findAll(userDtos);
        UserPersonDto userPersonDto;
        List<UserPersonDto> userPersonDtos = new ArrayList<>();
        for (UserDto user : userDtos) {
            userPersonDto = new UserPersonDto();
            userPersonDto.setUser(user);
            userPersonDto.setPerson(personList.stream().filter(a -> a.getId() == user.getPersonId()).findFirst().orElse(null));
            userPersonDtos.add(userPersonDto);
        }
        return userPersonDtos;
    }
}
