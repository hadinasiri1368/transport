package org.transport.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.transport.common.CommonUtils;
import org.transport.constant.Const;
import org.transport.common.ObjectMapperUtils;
import org.transport.dto.*;
import org.transport.dto.Response.NeshanElementDto;
import org.transport.dto.Response.PriceDto;
import org.transport.model.*;
import org.transport.repository.JPA;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {
    @Autowired
    private JPA<Order, Long> orderJPA;
    @Autowired
    private JPA<OrderDetail, Long> orderDetailJPA;
    @Autowired
    private JPA<OrderImage, Long> orderImageJPA;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private JPA<Car, Long> carJPA;
    @Autowired
    private JPA<Driver, Long> driverJPA;
    @Autowired
    private UserCompanyService userCompanyService;
    @Autowired
    private AuthenticationServiceProxy authenticationServiceProxy;
    @Autowired
    private NeshanMapServiceImpl neshanMapService;
    @Autowired
    private BasicDataServiceProxy basicDataServiceProxy;
    @Autowired
    private PersonService personService;

    @Value("${PageRequest.page}")
    private Integer page;
    @Value("${PageRequest.size}")
    private Integer size;

    private void insertDetail(Order order, List<OrderDetail> orderDetails, List<OrderImage> orderImages, Long userId) throws Exception {
        if (!CommonUtils.isNull(orderDetails) && orderDetails.size() > 0) {
            for (OrderDetail orderDetail : orderDetails) {
                orderDetail.setId(null);
                orderDetail.setOrder(order);
                orderDetail.setInsertedUserId(userId);
                orderDetail.setInsertedDateTime(new Date());
                orderDetailJPA.save(orderDetail);
            }
        }
        if (!CommonUtils.isNull(orderImages) && orderImages.size() > 0) {
            for (OrderImage orderImage : orderImages) {
                orderImage.setId(null);
                orderImage.setOrderId(order.getId());
                orderImage.setInsertedUserId(userId);
                orderImage.setInsertedDateTime(new Date());
                orderImageJPA.save(orderImage);
            }
        }
        checkOrder(order.getId());
    }

    private void updateDetail(Order order, List<OrderDetail> orderDetails, List<OrderImage> orderImages, Long userId) throws Exception {
        if (!CommonUtils.isNull(orderDetails) && orderDetails.size() > 0) {
            for (OrderDetail orderDetail : orderDetails) {
                orderDetail.setOrder(order);
                orderDetail.setUpdatedUserId(userId);
                orderDetail.setUpdatedDateTime(new Date());
                orderDetailJPA.update(orderDetail);
            }
        }
        if (!CommonUtils.isNull(orderImages) && orderImages.size() > 0) {
            for (OrderImage orderImage : orderImages) {
                orderImage.setOrderId(order.getId());
                orderImage.setUpdatedUserId(userId);
                orderImage.setUpdatedDateTime(new Date());
                orderImageJPA.update(orderImage);
            }
        }
        checkOrder(order.getId());
    }

    private int deleteDetail(Long orderId, boolean deleteDetail, boolean deleteImage) {
        int returnValue = 0;
        if (deleteDetail) {
            returnValue += entityManager.createQuery("delete from orderDetail entity where entity.order.id =:orderId")
                    .setParameter("orderId", orderId).executeUpdate();
        }
        if (deleteImage) {
            returnValue += entityManager.createQuery("delete from orderImage entity where entity.order.id =:orderId")
                    .setParameter("orderId", orderId).executeUpdate();
        }
        returnValue += entityManager.createQuery("delete from order entity where entity.id =:orderId")
                .setParameter("orderId", orderId).executeUpdate();
        return returnValue;
    }

    private int deleteDetail(Long orderId, List<Long> detailIds, List<Long> ImageIds) {
        int returnValue = 0;
        if (!CommonUtils.isNull(detailIds) && detailIds.size() > 0) {
            returnValue += entityManager.createQuery("delete from orderDetail where orderDetail.order.id =:orderId and orderDetail.id in (:detailIds)")
                    .setParameter("orderId", orderId).setParameter("detailIds", detailIds).executeUpdate();
        }
        if (!CommonUtils.isNull(ImageIds) && ImageIds.size() > 0) {
            returnValue += entityManager.createQuery("delete from orderImage where orderImage.order.id =:orderId and orderImage.id in (:ImageIds)")
                    .setParameter("orderId", orderId).setParameter("ImageIds", ImageIds).executeUpdate();
        }
        checkOrder(orderId);
        return returnValue;
    }

    @Transactional
    public void insert(Order order, Long userId) throws Exception {
        order.setId(null);
        order.setInsertedUserId(userId);
        order.setInsertedDateTime(new Date());
        order.setOrderStatusId(Const.ORDER_STATUS_DRAFT);
        orderJPA.save(order);
        insertDetail(order, CommonUtils.isNull(order.getOrderDetails()) ? null : order.getOrderDetails()
                , CommonUtils.isNull(order.getOrderImages()) ? null : order.getOrderImages(), userId);
    }

    @Transactional
    public void insert(Long orderId, List<OrderDetail> orderDetails, List<OrderImage> orderImages, Long userId) throws Exception {
        Order order = findOne(orderId);
        insertDetail(order, orderDetails, orderImages, userId);
    }

    @Transactional
    public void insert(Long orderId, List<OrderDetail> orderDetails, Long userId) throws Exception {
        Order order = findOne(orderId);
        insertDetail(order, orderDetails, null, userId);
    }

    @Transactional
    public void insert(Long orderId, Long userId, List<OrderImage> orderImages) throws Exception {
        Order order = findOne(orderId);
        insertDetail(order, null, orderImages, userId);
    }

    @Transactional
    public void update(Order order, Long userId) throws Exception {
        order.setUpdatedUserId(userId);
        order.setUpdatedDateTime(new Date());
        orderJPA.update(order);
    }

    @Transactional
    public void update(Order order, List<OrderDetail> orderDetails, Long userId) throws Exception {
        order.setUpdatedUserId(userId);
        order.setUpdatedDateTime(new Date());
        orderJPA.update(order);
        updateDetail(order, orderDetails, null, userId);
    }

    @Transactional
    public void update(Order order, Long userId, List<OrderImage> orderImages) throws Exception {
        order.setUpdatedUserId(userId);
        order.setUpdatedDateTime(new Date());
        orderJPA.update(order);
        updateDetail(order, null, orderImages, userId);
    }

    @Transactional
    public int delete(Long orderId) {
        return deleteDetail(orderId, true, true);
    }

    @Transactional
    public int deleteDetail(Long orderId) {
        return deleteDetail(orderId, true, false);
    }

    @Transactional
    public int deleteImage(Long orderId) {
        return deleteDetail(orderId, false, true);
    }

    @Transactional
    public int deleteImage(Long orderId, List<Long> detailIds) {
        return deleteDetail(orderId, detailIds, null);
    }

    @Transactional
    public int deleteImage(List<Long> imageIds, Long orderId) {
        return deleteDetail(orderId, null, imageIds);
    }

    @Transactional
    public int deleteImage(Long orderId, List<Long> detailIds, List<Long> imageIds) {
        return deleteDetail(orderId, detailIds, imageIds);
    }

    private void checkOrder(Long orderId) {
        Query query = entityManager.createQuery("select entity from orderDetail  entity where entity.order.id=:orderId");
        query.setParameter("orderId", orderId);
        List<OrderDetail> orderDetails = (List<OrderDetail>) query.getResultList();
        if (CommonUtils.isNull(orderDetails) || orderDetails.size() == 0) {
            throw new RuntimeException("order detail is null");
        }
    }

    private List<OrderDetail> getOrderDetail(Long orderId) {
        Query query = entityManager.createQuery("select entity from orderDetail  entity where entity.order.id=:orderId");
        query.setParameter("orderId", orderId);
        List<OrderDetail> orderDetails = (List<OrderDetail>) query.getResultList();
        if (CommonUtils.isNull(orderDetails) || orderDetails.size() == 0) {
            throw new RuntimeException("order detail is null");
        }
        return orderDetails;
    }


    public Page<Order> findAll(Long userId, String token, String uuid, Integer page, Integer size) throws Exception {
        List<Order> returnValue = new ArrayList<>();
        List<RoleDto> roleDtos = new ArrayList<>();
        try {
            UserDto userDto = ObjectMapperUtils.map(authenticationServiceProxy.getUser(token, uuid), UserDto.class);
            if (CommonUtils.isNull(userDto)) {
                throw new RuntimeException("can not identify token");
            }
            String hql;
            List<Long> orderIds = new ArrayList<>();
            if (userDto.getIsAdmin()) {
                if (CommonUtils.isNull(page) && CommonUtils.isNull(size)) {
                    return orderJPA.findAllWithPaging(Order.class);
                }
                PageRequest pageRequest = PageRequest.of(CommonUtils.isNull(page, this.page), CommonUtils.isNull(size, this.size));
                return orderJPA.findAllWithPaging(Order.class, pageRequest);
            }
            roleDtos = authenticationServiceProxy.listRole(token, uuid);
            if (CommonUtils.isNull(roleDtos))
                throw new RuntimeException("connection.failed");
            if (roleDtos.stream().filter(a -> a.getId() == Const.ROLE_CUSTOMER).count() > 0) {
                hql = "select o from order o where o.userId=:userId";
                Query query = entityManager.createQuery(hql);
                Map<String, Object> param = new HashMap<>();
                param.put("userId", userId);
                returnValue.addAll(orderJPA.listByQuery(query, param));
                orderIds = returnValue.stream().map(entity -> entity.getId()).collect(Collectors.toList());
            }
            if (roleDtos.stream().filter(a -> a.getId() == Const.ROLE_STAFF).count() > 0) {
                List<Long> userIds = userCompanyService.getUserColleague(userDto.getId());
                hql = "select o from order o where o.userId in (:userIds) and o.id not in (:orderids) ";
                Query query = entityManager.createQuery(hql);
                Map<String, Object> param = new HashMap<>();
                param.put("userIds", userIds);
                if (CommonUtils.isNull(orderIds) || orderIds.size() == 0)
                    orderIds = Arrays.asList(-1L);
                param.put("orderids", orderIds);
                returnValue.addAll(orderJPA.listByQuery(query, param));
                orderIds = returnValue.stream().map(entity -> entity.getId()).collect(Collectors.toList());
            }
            if (roleDtos.stream().filter(a -> a.getId() == Const.ROLE_DRIVER).count() > 0) {
                hql = "select o from order o \n" +
                        "where o.onlyMyCompanyDriver = false \n" +
                        "and o.orderStatusId in (:orderStatusId) \n" +
                        "and o.car is null \n" +
                        "and o.driver is null \n" +
                        "and o.id not in (:orderIds) ";
                Query query = entityManager.createQuery(hql);
                Map<String, Object> param = new HashMap<>();
                param.put("orderStatusId", Const.ORDER_STATUS_WAIT_FOR_CONFIRM);
                if (CommonUtils.isNull(orderIds) || orderIds.size() == 0)
                    orderIds = Arrays.asList(-1L);
                param.put("orderIds", orderIds);
                returnValue.addAll(orderJPA.listByQuery(query, param));
                orderIds = returnValue.stream().map(entity -> entity.getId()).collect(Collectors.toList());

                hql = "select o from order o \n" +
                        "where o.onlyMyCompanyDriver = true \n" +
                        "and o.orderStatusId in (:orderStatusId) \n" +
                        "and o.userId in (select uc.userId from userCompany uc \n" +
                        "where uc.company.id in (select u.company.id from userCompany u where u.userId = o.userId)) \n" +
                        "and o.car is null \n" +
                        "and o.driver is null \n" +
                        "and o.id not in (:orderIds) ";
                query = entityManager.createQuery(hql);
                param = new HashMap<>();
                param.put("orderStatusId", Const.ORDER_STATUS_WAIT_FOR_CONFIRM);
                if (CommonUtils.isNull(orderIds) || orderIds.size() == 0)
                    orderIds = Arrays.asList(-1L);
                param.put("orderIds", orderIds);
                returnValue.addAll(orderJPA.listByQuery(query, param));
            }
        } catch (Exception e) {
            throw e;
        }
        if (CommonUtils.isNull(page) && CommonUtils.isNull(size)) {
            return CommonUtils.listPaging(returnValue);
        }
        PageRequest pageRequest = PageRequest.of(CommonUtils.isNull(page, this.page), CommonUtils.isNull(size, this.size));
        return CommonUtils.listPaging(returnValue, pageRequest);
    }

    public Order findOne(Long id) {
        return orderJPA.findOne(Order.class, id);
    }


    @Transactional
    public void acceptOrderCarDriver(Long orderId, Long carId, Long userId, String token, String uuid) throws Exception {

        UserDto userDto = ObjectMapperUtils.map(authenticationServiceProxy.getUser(token, uuid), UserDto.class);
        List<RoleDto> roleDtos = new ArrayList<>();
        if (CommonUtils.isNull(userDto))
            throw new RuntimeException("2002");

        Order order = findOne(orderId);
        if (CommonUtils.isNull(order))
            throw new RuntimeException("2006");

        if (!order.getOrderStatusId().equals(Const.ORDER_STATUS_WAIT_FOR_CONFIRM))
            throw new RuntimeException("2014");

        Car car = carJPA.findOne(Car.class, carId);
        if (CommonUtils.isNull(car))
            throw new RuntimeException("2013");
        Page<Order> orders = findAll(userId, token, uuid, 0, 100);
        if (orders.isEmpty())
            throw new RuntimeException("2012");

        roleDtos = authenticationServiceProxy.listRole(token, uuid);
        if (roleDtos.stream().noneMatch(a -> a.getId().equals(Const.ROLE_DRIVER))) {
            throw new RuntimeException("2020");
        }

        if (orders.stream().filter(a -> a.getId().equals(orderId)).count() <= 0L)
            throw new RuntimeException("2011");

        String hql = "select d from driver d where d.person.id =:personId";
        Query query = entityManager.createQuery(hql);
        Map<String, Object> param = new HashMap<>();
        param.put("personId", userDto.getPersonId());
        List<Driver> drivers = (List<Driver>) driverJPA.listByQuery(query, param);
        Driver driver = drivers.get(0);
        order.setOrderStatusId(Const.ORDER_STATUS_CONFIRMED);
        order.setCar(car);
        order.setDriver(driver);
        update(order, userId);
    }

    @Transactional
    public Long changeOrderStatus(Long orderId, Long userId, String token, String uuid) throws Exception {

        UserDto userDto = ObjectMapperUtils.map(authenticationServiceProxy.getUser(token, uuid), UserDto.class);
        if (CommonUtils.isNull(userDto))
            throw new RuntimeException("2002");

        Order order = findOne(orderId);
        String hql = "select d from driver d where d.person.id =:personId";
        Query query = entityManager.createQuery(hql);
        Map<String, Object> param = new HashMap<>();
        param.put("personId", userDto.getPersonId());
        List<Driver> drivers = (List<Driver>) driverJPA.listByQuery(query, param);
        Driver driver = drivers.get(0);

        if (CommonUtils.isNull(order))
            throw new RuntimeException("2006");

        if (order.getOrderStatusId().equals(Const.ORDER_STATUS_CANCELLED_CUSTOMER))
            throw new RuntimeException("2009");

        if (order.getOrderStatusId().equals(Const.ORDER_STATUS_CANCELLED_DRIVER))
            throw new RuntimeException("2010");

        if (order.getOrderStatusId().equals(Const.ORDER_STATUS_DRAFT)) {
            order.setOrderStatusId(Const.ORDER_STATUS_WAIT_FOR_CONFIRM);
            update(order, userId);
            return order.getOrderStatusId();
        }

        if (order.getOrderStatusId().equals(Const.ORDER_STATUS_CONFIRMED) && order.getDriver().getId().equals(driver.getId())) {
            order.setOrderStatusId(Const.ORDER_STATUS_WAIT_FOR_LOADING);
            update(order, userId);
            return order.getOrderStatusId();
        }

        if (order.getOrderStatusId().equals(Const.ORDER_STATUS_WAIT_FOR_LOADING) && order.getDriver().getId().equals(driver.getId())) {
            order.setOrderStatusId(Const.ORDER_STATUS_CAR_IN_LOADING_ORIGIN);
            update(order, userId);
            return order.getOrderStatusId();
        }

        if (order.getOrderStatusId().equals(Const.ORDER_STATUS_CAR_IN_LOADING_ORIGIN) && order.getDriver().getId().equals(driver.getId())) {
            order.setOrderStatusId(Const.ORDER_STATUS_LOADED);
            update(order, userId);
            return order.getOrderStatusId();
        }

        if (order.getOrderStatusId().equals(Const.ORDER_STATUS_LOADED) && order.getDriver().getId().equals(driver.getId())) {
            order.setOrderStatusId(Const.ORDER_STATUS_CARRYING_CARGO);
            update(order, userId);
            return order.getOrderStatusId();
        }

        if (order.getOrderStatusId().equals(Const.ORDER_STATUS_CARRYING_CARGO) && order.getDriver().getId().equals(driver.getId())) {
            order.setOrderStatusId(Const.ORDER_STATUS_CAR_IN_DESTINATION);
            update(order, userId);
            return order.getOrderStatusId();
        }

        if (order.getOrderStatusId().equals(Const.ORDER_STATUS_CAR_IN_DESTINATION) && order.getDriver().getId().equals(driver.getId())) {
            order.setOrderStatusId(Const.ORDER_STATUS_DELIVERED);
            update(order, userId);
            return order.getOrderStatusId();
        }

        if (order.getOrderStatusId().equals(Const.ORDER_STATUS_DELIVERED) && order.getUserId().equals(userId)) {
            order.setOrderStatusId(Const.ORDER_STATUS_TERMINATED);
            update(order, userId);
            return order.getOrderStatusId();
        }

        throw new RuntimeException("2001");
    }

    @Transactional
    public void cancelledOrder(Long orderId, Long userId, String token, String uuid) throws Exception {
        UserDto userDto = ObjectMapperUtils.map(authenticationServiceProxy.getUser(token, uuid), UserDto.class);
        if (CommonUtils.isNull(userDto))
            throw new RuntimeException("2002");
        Order order = findOne(orderId);
        String hql = "select d from driver d where d.person.id =:personId";
        Query query = entityManager.createQuery(hql);
        Map<String, Object> param = new HashMap<>();
        param.put("personId", userDto.getPersonId());
        List<Driver> drivers = (List<Driver>) driverJPA.listByQuery(query, param);
        Driver driver = drivers.get(0);

        if (CommonUtils.isNull(order))
            throw new RuntimeException("2006");

        if (order.getOrderStatusId().equals(Const.ORDER_STATUS_LOADED) || order.getOrderStatusId().equals(Const.ORDER_STATUS_CARRYING_CARGO)
                || order.getOrderStatusId().equals(Const.ORDER_STATUS_CAR_IN_DESTINATION) || order.getOrderStatusId().equals(Const.ORDER_STATUS_DELIVERED))
            throw new RuntimeException("2007");

        if (order.getOrderStatusId().equals(Const.ORDER_STATUS_CANCELLED_DRIVER) || order.getOrderStatusId().equals(Const.ORDER_STATUS_CANCELLED_CUSTOMER))
            throw new RuntimeException("2008");

        if (order.getOrderStatusId().equals(Const.ORDER_STATUS_WAIT_FOR_CONFIRM) && order.getUserId().equals(userId)) {
            order.setOrderStatusId(Const.ORDER_STATUS_CANCELLED_CUSTOMER);
            update(order, userId);
            return;
        }

        if (order.getOrderStatusId().equals(Const.ORDER_STATUS_WAIT_FOR_CONFIRM) || order.getOrderStatusId().equals(Const.ORDER_STATUS_CAR_IN_LOADING_ORIGIN)
                || order.getOrderStatusId().equals(Const.ORDER_STATUS_WAIT_FOR_LOADING)) {
            if (order.getUserId().equals(userId)) {
                order.setOrderStatusId(Const.ORDER_STATUS_CANCELLED_CUSTOMER);
            } else {
                order.setOrderStatusId(Const.ORDER_STATUS_CANCELLED_DRIVER);
            }
            update(order, userId);
            return;
        }
        throw new RuntimeException("2001");
    }

    public List<PriceDto> calculationPrice(Long orderId, Long companyId, String token, String uuid) throws Exception {
        UserDto userDto = ObjectMapperUtils.map(authenticationServiceProxy.getUser(token, uuid), UserDto.class);
        if (CommonUtils.isNull(userDto))
            throw new RuntimeException("2002");
        List<Person> companyList = personService.findPersonsRole(Const.ROLE_COMPANY, token, uuid);
        if (CommonUtils.isNull(companyList))
            throw new RuntimeException("2002");
        if (!CommonUtils.isNull(companyId))
            companyList = companyList.stream().filter(a -> a.getId() == companyId).collect(Collectors.toList());
        List<PriceDto> priceDtos = new ArrayList<>();
        for (Person person : companyList) {
            priceDtos.add(calculatePricePerCompany(orderId, person.getId(), token, uuid));
        }
        return priceDtos;
    }

    public PriceDto calculatePricePerCompany(Long orderId, Long companyID, String token, String uuid) throws Exception {
        UserDto userDto = ObjectMapperUtils.map(authenticationServiceProxy.getUser(token, uuid), UserDto.class);
        if (CommonUtils.isNull(userDto))
            throw new RuntimeException("2002");
        Order order = findOne(orderId);
        if (CommonUtils.isNull(order)) {
            throw new RuntimeException("2006");
        }
        List<OrderDetail> orderDetails = getOrderDetail(orderId);
        if (orderDetails.isEmpty()) {
            throw new RuntimeException("2042");
        }
        List<Long> weightList = new ArrayList<>();
        List<Long> loadingTypeList = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetails) {
            weightList.add(orderDetail.getWeight());
            loadingTypeList.add(orderDetail.getLoadingTypeId());
        }
        long totalWeight = weightList.stream().mapToLong(Long::longValue).sum();
        List<Float> loadingTypeFactors = new ArrayList<>();
        for (Long loadingTypeCode : loadingTypeList) {
            LoadingTypeDto loadingTypeDto = basicDataServiceProxy.loadingTypeValue(token, uuid, loadingTypeCode, companyID);
            loadingTypeFactors.add(loadingTypeDto.getFactorValue());
        }
        float totalLoadingTypeFactor = (float) loadingTypeFactors.stream().mapToLong(Float::longValue).sum();
        NeshanElementDto neshanElementDto = neshanMapService.getDistanceWithTraffic(order.getSenderLatitude(), order.getSenderLongitude(), order.getReceiverLatitude(), order.getReceiverLongitude());
        if (neshanElementDto == null) {
            throw new RuntimeException("2041");
        }
        int distance = neshanElementDto.getDistance().getValue();
        int duration = neshanElementDto.getDuration().getValue();

        Page<CarCapacityDto> carCapacityPage = basicDataServiceProxy.listCarCapacity(token, uuid);
        if (carCapacityPage.isEmpty()) {
            throw new RuntimeException("2037");
        }
        List<CarCapacityDto> carCapacityList = carCapacityPage.getContent();
        Optional<CarCapacityDto> carCapacityOptional = carCapacityList.stream()
                .filter(a -> a.getMaxCapacity() != null && a.getMaxCapacity() > totalWeight)
                .min(Comparator.comparingLong(CarCapacityDto::getMaxCapacity));
        if (carCapacityOptional.isEmpty()) {
            throw new RuntimeException("2037");
        }
        CarGroupDto carGroupDto = basicDataServiceProxy.carGroupValue(token, uuid, order.getCarTypeId(), carCapacityOptional.get().getId(), companyID);
        if (CommonUtils.isNull(carGroupDto)) {
            log.info("CarGroupDto is null for carTypeId: {}, carCapacityId: {}, companyID: {}", order.getCarTypeId(), carCapacityOptional.get().getId(), companyID);
            throw new RuntimeException("2038");
        }
        Float carGroupFactorValue = carGroupDto.getFactorValue();
        ParametersDto parametersDtoTime = basicDataServiceProxy.parametersValue(token, uuid, Const.ARRIVAL_TIME_FACTOR, companyID);
        if (CommonUtils.isNull(parametersDtoTime)) {
            throw new RuntimeException("2043");
        }
        Float timeFactor = CommonUtils.floatValue(parametersDtoTime.getValue());
        ParametersDto parametersDtoDistance = basicDataServiceProxy.parametersValue(token, uuid, Const.TON_KILOMETERS, companyID);
        if (CommonUtils.isNull(parametersDtoDistance)) {
            throw new RuntimeException("2043");
        }
        Float distanceFactor = CommonUtils.floatValue(parametersDtoDistance.getValue());
        float price = ((distance / 1000) * distanceFactor) * (1 + (duration * timeFactor)) * (1 + totalLoadingTypeFactor) * (1 + carGroupFactorValue);
        PriceDto priceDto = new PriceDto();
        priceDto.setCompanyId(companyID);
        priceDto.setDistanceFactor(distanceFactor);
        priceDto.setTimeFactor(timeFactor);
        priceDto.setTotalLoadingTypeFactor(totalLoadingTypeFactor);
        priceDto.setCarGroupFactorValue(carGroupFactorValue);
        priceDto.setPrice(price);
        return priceDto;
    }
}

