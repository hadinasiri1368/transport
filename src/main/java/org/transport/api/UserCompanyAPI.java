package org.transport.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.transport.common.CommonUtils;
import org.transport.dto.UserCompanyDto;
import org.transport.model.Person;
import org.transport.model.UserCompany;
import org.transport.service.UserCompanyService;

import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class UserCompanyAPI {
    @Autowired
    private UserCompanyService service;

    @PostMapping(path = "/api/userCompany/add")
    public Long addUserCompany(@RequestBody UserCompanyDto userCompanyDto, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request), uuid);
        UserCompany userCompany = new UserCompany();
        Person person = new Person();
        userCompany.setId(userCompanyDto.getId());
        person.setId(userCompanyDto.getCompanyId());
        userCompany.setCompany(person);
        userCompany.setUserId(userCompanyDto.getUserId());
        service.insert(userCompany, userId, CommonUtils.getToken(request), uuid);
        return userCompany.getId();
    }

    @PutMapping(path = "/api/userCompany/edit")
    public Long editUserCompany(@RequestBody UserCompanyDto userCompanyDto, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request), uuid);
        UserCompany userCompany = new UserCompany();
        Person person = new Person();
        userCompany.setId(userCompanyDto.getId());
        person.setId(userCompanyDto.getCompanyId());
        userCompany.setCompany(person);
        userCompany.setUserId(userCompanyDto.getUserId());
        service.update(userCompany, userId, CommonUtils.getToken(request), uuid);
        return userCompany.getId();
    }

    @DeleteMapping(path = "/api/userCompany/remove/{id}")
    public Long removeGUserCompany(@PathVariable Long id) {
        service.delete(id);
        return id;
    }

    @GetMapping(path = "/api/userCompany/{id}")
    public UserCompany getUserCompany(@PathVariable Long id) {
        return service.findOne(id);
    }

    @GetMapping(path = "/api/userCompanyByUserId/{userId}")
    public List<UserCompany> getUserCompanyByUserId(@PathVariable Long userId) {
        return service.findByUserId(userId);
    }

    @GetMapping(path = "/api/userCompany")
    public Page<UserCompany> listUserCompany(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        return service.findAll(UserCompany.class,page,size);
    }
}
