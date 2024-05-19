package org.transport.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.transport.dto.RoleDto;
import org.transport.dto.UserDto;

import java.util.List;
import java.util.Map;

@FeignClient("AUTHENTICATION")
public interface AuthenticationServiceProxy {
    @GetMapping(path = "/api/user/role")
    List<RoleDto> listRole(@RequestHeader("Authorization") String token, @RequestHeader("X-UUID") String uuid, @RequestParam(value = "userId") Long userId);

    @GetMapping(path = "/api/user/role")
    List<RoleDto> listRole(@RequestHeader("Authorization") String token, @RequestHeader("X-UUID") String uuid);

    @GetMapping(path = "/getUserId")
    String getUserId(@RequestParam("token") String token, @RequestHeader("X-UUID") String uuid);

    @GetMapping(path = "/checkValidationToken")
    String checkValidationToken(@RequestParam("token") String token, @RequestHeader("X-UUID") String uuid, @RequestParam("url") String url);

    @GetMapping(path = "/getUser")
    Map getUser(@RequestParam("token") String token, @RequestHeader("X-UUID") String uuid);

    @GetMapping(path = "/api/findUser/{personId}")
    UserDto findPersonUser(@RequestHeader("Authorization") String token, @RequestHeader("X-UUID") String uuid, @PathVariable Long personId);

}
