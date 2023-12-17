package org.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private boolean isAdmin;
    private boolean isActive;
    private Long personId;
}
