package org.transport.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.transport.dto.RoleDto;
import org.transport.dto.UserDto;

import java.util.List;
import java.util.Map;

@FeignClient("AUTHENTICATION")
public interface AuthenticationServiceProxy {
    @GetMapping(path = "/authentication/user/role")
    List<RoleDto> listRole(@RequestHeader("Authorization") String token, @RequestHeader("X-UUID") String uuid, @RequestParam(value = "userId") Long userId);

    @GetMapping(path = "/authentication/user/role")
    List<RoleDto> listRole(@RequestHeader("Authorization") String token, @RequestHeader("X-UUID") String uuid);

    @GetMapping(path = "/authentication/getUserId")
    String getUserId(@RequestParam("token") String token, @RequestHeader("X-UUID") String uuid);

    @GetMapping(path = "/authentication/checkValidationToken")
    String checkValidationToken(@RequestParam("token") String token, @RequestHeader("X-UUID") String uuid, @RequestParam("url") String url);

    @GetMapping(path = "/authentication/getUser")
    Map getUser(@RequestParam("token") String token, @RequestHeader("X-UUID") String uuid);

    @GetMapping(path = "/authentication/findUser/{personId}")
    UserDto findPersonUser(@RequestHeader("Authorization") String token, @RequestHeader("X-UUID") String uuid, @PathVariable Long personId);

    @GetMapping(path = "/authentication/UsersPerRole/{roleId}")
    List<UserDto> findAllUserRole(@RequestHeader("Authorization") String token, @RequestHeader("X-UUID") String uuid, @PathVariable Long roleId);

}
