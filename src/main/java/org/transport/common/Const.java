package org.transport.common;

public class Const {
    //ROLE
    public static final Long ROLE_CUSTOMER = 1L;
    public static final Long ROLE_DRIVER = 2L;
    public static final Long ROLE_STAFF = 3L;


    //ORDER_STATUS
    public static final Long ORDER_STATUS_DRAFT= 1L;
    public static final Long ORDER_STATUS_WAIT_FOR_CONFIRM = 2L;
    public static final Long ORDER_STATUS_CONFIRMED = 3L;
    public static final Long ORDER_STATUS_TERMINATED = 5L;
    public static final Long ORDER_STATUS_WAIT_FOR_LOADING = 9L;
    public static final Long ORDER_STATUS_CAR_IN_LOADING_ORIGIN = 10L;
    public static final Long ORDER_STATUS_LOADED = 11L;
    public static final Long ORDER_STATUS_CARRYING_CARGO = 12L;
    public static final Long ORDER_STATUS_CAR_IN_DESTINATION = 13L;
    public static final Long ORDER_STATUS_DELIVERED = 14L;
    public static final Long ORDER_STATUS_CANCELLED_DRIVER = 15L;
    public static final Long ORDER_STATUS_CANCELLED_CUSTOMER = 16L;

    //REQUEST_STATUS
    public static final Long REQUEST_STATUS_CONFIRM = 2L;
}
