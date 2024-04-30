package org.transport.common;

public enum ChangeRequest {

    ACCEPT (1l, "تایید"),
    REJECT(2l, "رد"),
    ;

    ChangeRequest(Long value, String name) {
        this.value = value;
        this.name = name;
    }

    private final Long value;
    private final String name;

    public Long getValue() {
        return value;
    }

    public String getName() {
        return name;
    }


}
