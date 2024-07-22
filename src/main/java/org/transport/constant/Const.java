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
    public static final Long LOADING_TYPE_TRANSPORTING_FOOD_MEDICINE_REQUIRES_REFRIGERATION = 1L;
    public static final Long LOADING_TYPE_CARRYING_FOOD_MEDICINE_WITHOUT_NEED_FOR_REFRIGERATOR = 2L;
    public static final Long LOADING_TYPE_CARRYING_FRUITS_VEGETABLES = 3L;
    public static final Long LOADING_TYPE_TRANSPORTING_AGRICULTURAL_PRODUCTS = 4L;
    public static final Long LOADING_TYPE_TRANSPORTING_CHEMICALS_PETROLEUM_FUELS = 5L;
    public static final Long LOADING_TYPE_CARRYING_TOILETRIES = 6L;
    public static final Long LOADING_TYPE_DRY_WASTE_TRANSPORTATION = 7L;
    public static final Long LOADING_TYPE_TRANSPORTING_HOSPITAL_WASTE = 8L;
    public static final Long LOADING_TYPE_CARRYING_HOME_FURNITURE = 9L;
    public static final Long LOADING_TYPE_CARRYING_CONSTRUCTION_CONSTRUCTION_MATERIALS = 10L;

    //CAR_CAPACITY
    private static final Long CAR_CAPACITY_MAXIMUM_CAPACITY_OF_THREE_TONS = 1L;
    private static final Long CAR_CAPACITY_GREATER_THREE_TONS_TO_MAXIMUM_SIX_TONS = 2L;
    private static final Long CAR_CAPACITY_GREATER_THAN_SIX_TONS_TO_MAXIMUM_TEN_TONS = 3L;
    private static final Long CAR_CAPACITY_MORE_THAN_TEN_TONS_TO_MAXIMUM_TWENTY_TONS = 4L;
    private static final Long CAR_CAPACITY_MORE_THAN_TWENTY_TONS_TO_MAXIMUM_THIRTY_TONS = 5L;
    private static final Long CAR_CAPACITY_MORE_THAN_THIRTY_TONS_TO_MAXIMUM_FORTY_TONS = 6L;
    private static final Long CAR_CAPACITY_MORE_THAN_FORTY_TONS_TO_MAXIMUM_FIFTY_TONS = 7L;
    private static final Long CAR_CAPACITY_MORE_THAN_FIFTY_TONS = 8L;

    //PARAMETERS
    public static final String TON_KILOMETERS = "TON_KILOMETERS";
    public static final String ARRIVAL_TIME_FACTOR = "ARRIVAL_TIME_FACTOR";

}
