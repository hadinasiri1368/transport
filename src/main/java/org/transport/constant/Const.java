package org.transport.constant;

import jdk.dynalink.beans.StaticClass;

public class Const {
    //ROLE
    public static final Long ROLE_CUSTOMER = 1L;
    public static final Long ROLE_DRIVER = 2L;
    public static final Long ROLE_STAFF = 3L;
    public static final Long ROLE_COMPANY = 4L;


    //ORDER_STATUS
    public static final Long ORDER_STATUS_DRAFT = 1L;
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
    public static final Long REQUEST_STATUS_PENDING = 1L;
    public static final Long REQUEST_STATUS_CONFIRM = 2L;
    public static final Long REQUEST_STATUS_REJECT = 3L;

    //LOADING_TYPE
    public static final Long TRANSPORTING_FOOD_MEDICINE_REQUIRES_REFRIGERATION = 1L;
    public static final Long CARRYING_FOOD_MEDICINE_WITHOUT_NEED_FOR_REFRIGERATOR = 2L;
    public static final Long CARRYING_FRUITS_VEGETABLES = 3L;
    public static final Long TRANSPORTING_AGRICULTURAL_PRODUCTS = 4L;
    public static final Long TRANSPORTING_CHEMICALS_PETROLEUM_FUELS = 5L;
    public static final Long CARRYING_TOILETRIES = 6L;
    public static final Long DRY_WASTE_TRANSPORTATION = 7L;
    public static final Long TRANSPORTING_HOSPITAL_WASTE = 8L;
    public static final Long CARRYING_HOME_FURNITURE = 9L;
    public static final Long CARRYING_CONSTRUCTION_CONSTRUCTION_MATERIALS = 10L;

    //PARAMETERS
    public static final String TON_KILOMETERS = "TON_KILOMETERS";
    public static final String ARRIVAL_TIME_FACTOR = "ARRIVAL_TIME_FACTOR";

}
