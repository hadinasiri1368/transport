package org.transport.api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.transport.common.CommonUtils;
import org.transport.dto.UserDto;
import org.transport.dto.UserPersonDto;
import org.transport.model.Person;
import org.transport.service.PersonService;

import java.util.ArrayList;
import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class PersonAPI {
    @Autowired
    private PersonService service;

    @PostMapping(path = "/transport/person/add")
    public Long addPerson(@RequestBody Person person, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request), uuid);
        service.insert(person, userId);
        return person.getId();
    }

    @PutMapping(path = "/transport/person/edit")
    public Long editPerson(@RequestBody Person person, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request), uuid);
        service.update(person, userId);
        return person.getId();
    }

    @DeleteMapping(path = "/transport/person/remove/{id}")
    public Long removePerson(@PathVariable Long id) {
        service.delete(id);
        return id;
    }

    @GetMapping(path = "/transport/person/{id}")
    public Person getPerson(@PathVariable Long id) {
        return service.findOne(id);
    }

    @GetMapping(path = "/transport/person")
    public Page<Person> listPerson(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        return service.findAll(Person.class,page,size);
    }

    @PostMapping(path = "/transport/personUser")
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

    @GetMapping(path = "/transport/personsRole/{roleId}")
    public List<Person> listPersonsRole(@PathVariable Long roleId, HttpServletRequest request){
        String uuid = request.getHeader("X-UUID");
        String token = CommonUtils.getToken(request);
        return service.findPersonsRole(roleId,token,uuid);
    }


}
