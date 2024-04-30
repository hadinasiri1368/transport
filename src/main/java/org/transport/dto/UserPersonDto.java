package org.transport.dto;

import lombok.*;
import org.transport.model.Person;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserPersonDto {
    private UserDto user;
    private Person person;
}
