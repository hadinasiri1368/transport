package org.transport.constant;

import lombok.Getter;

@Getter
public enum ChangeRequest {

    ACCEPT(1L, "تایید"),
    REJECT(2L, "رد"),
    ;

    ChangeRequest(Long value, String name) {
        this.value = value;
        this.name = name;
    }

    private final Long value;
    private final String name;

}
