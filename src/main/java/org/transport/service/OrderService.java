package org.transport.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.transport.common.CommonUtils;
import org.transport.model.*;
import org.transport.repository.JPA;

import java.util.Date;
import java.util.List;


@Service
public class OrderService {
    @Autowired
    private JPA<Order, Long> orderJPA;
    @Autowired
    private JPA<OrderDetail, Long> orderDetailJPA;
    @Autowired
    private JPA<OrderImage, Long> orderImageJPA;
    @Autowired
    private EntityManager entityManager;

    private void insertDetail(Order order, List<OrderDetail> orderDetails, List<OrderImage> orderImages, Long userId) {
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
                orderImage.setOrder(order);
                orderImage.setInsertedUserId(userId);
                orderImage.setInsertedDateTime(new Date());
                orderImageJPA.save(orderImage);
            }
        }
        checkOrder(order.getId());
    }

    private void updateDetail(Order order, List<OrderDetail> orderDetails, List<OrderImage> orderImages, Long userId) {
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
                orderImage.setOrder(order);
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
    public void insert(Order order, Long userId) {
        order.setId(null);
        order.setInsertedUserId(userId);
        order.setInsertedDateTime(new Date());
        orderJPA.save(order);
        insertDetail(order, CommonUtils.isNull(order.getOrderDetails()) ? null : order.getOrderDetails()
                , CommonUtils.isNull(order.getOrderImages()) ? null : order.getOrderImages(), userId);
    }

    @Transactional
    public void insert(Long orderId, List<OrderDetail> orderDetails, List<OrderImage> orderImages, Long userId) {
        Order order = findOne(orderId);
        insertDetail(order, orderDetails, orderImages, userId);
    }

    @Transactional
    public void insert(Long orderId, List<OrderDetail> orderDetails, Long userId) {
        Order order = findOne(orderId);
        insertDetail(order, orderDetails, null, userId);
    }

    @Transactional
    public void insert(Long orderId, Long userId, List<OrderImage> orderImages) {
        Order order = findOne(orderId);
        insertDetail(order, null, orderImages, userId);
    }

    @Transactional
    public void update(Order order, Long userId) {
        order.setUpdatedUserId(userId);
        order.setUpdatedDateTime(new Date());
        orderJPA.update(order);
    }

    @Transactional
    public void update(Order order, List<OrderDetail> orderDetails, Long userId) {
        order.setUpdatedUserId(userId);
        order.setUpdatedDateTime(new Date());
        orderJPA.update(order);
        updateDetail(order, orderDetails, null, userId);
    }

    @Transactional
    public void update(Order order, Long userId, List<OrderImage> orderImages) {
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

    public List<Order> findAll() {
        return orderJPA.findAll(Order.class);
    }

    public Order findOne(Long id) {
        return orderJPA.findOne(Order.class, id);
    }
}
