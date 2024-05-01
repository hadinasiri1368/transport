package org.transport.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.transport.dto.RoleDto;

import java.util.List;
import java.util.Map;

@FeignClient("AUTHENTICATION")
public interface AuthenticationServiceProxy {
    @GetMapping(path = "/api/user/role")
    List<RoleDto> listRole(@RequestHeader("Authorization") String token, @RequestParam(value = "userId") Long userId);

    @GetMapping(path = "/api/user/role")
    List<RoleDto> listRole(@RequestHeader("Authorization") String token);

    @GetMapping(path = "/getUserId")
    String getUserId(@ModelAttribute("token") String token);

    @GetMapping(path = "/checkValidationToken")
    String checkValidationToken(@RequestParam("token") String token, @RequestParam("url") String url);

    @GetMapping(path = "/getUser")
    Map getUser(@ModelAttribute("token") String token);

}
