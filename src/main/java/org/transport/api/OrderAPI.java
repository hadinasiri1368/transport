package org.transport.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.transport.common.CommonUtils;
import org.transport.common.MapperUtil;
import org.transport.common.ObjectMapperUtils;
import org.transport.dto.OrderDetailDto;
import org.transport.dto.OrderDto;
import org.transport.model.*;
import org.transport.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class OrderAPI {
    @Autowired
    private OrderService service;

    @PostMapping(path = "/api/order/add")
    public Long addOrder(@RequestBody OrderDto orderDto, HttpServletRequest request) {
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request));
        Order order = ObjectMapperUtils.map(orderDto, Order.class);
        order.setOrderDetails(MapperUtil.mapToOrderDetail(orderDto.getOrderDetails()));
        service.insert(order, userId);
        return order.getId();
    }

    @PostMapping(path = "/api/orderDetail/add")
    public Long addOrderDetail(@RequestBody List<OrderDetailDto> orderDetailDtos, HttpServletRequest request) {
        validationData(orderDetailDtos, null);
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request));
        List<OrderDetail> orderDetails = MapperUtil.mapToOrderDetail(orderDetailDtos);
        service.insert(orderDetailDtos.get(0).getOrderId(), orderDetails, userId);
        return orderDetailDtos.get(0).getOrderId();
    }

    @PostMapping(path = "/api/orderImage/add", consumes = {"multipart/form-data"})
    public Long addOrderImage(@RequestParam("orderId") Long orderId, @RequestParam("image") MultipartFile[] multipartFiles, HttpServletRequest request) {
        try {
            Long userId = CommonUtils.getUserId(CommonUtils.getToken(request));
            List<OrderImage> orderImages = new ArrayList<>();
            for (MultipartFile multipartFile : multipartFiles) {
                orderImages.add(OrderImage.builder().pic(multipartFile.getBytes()).build());
            }
            service.insert(orderId, userId, orderImages);
            return CommonUtils.longValue(multipartFiles.length);
        } catch (Exception e) {
            log.error("save image error: " + e.getMessage());
        }
        return 0l;
    }

    @PostMapping(path = "/api/order/remove/{id}")
    public Long removeOrder(@PathVariable Long id) {
        service.delete(id);
        return id;
    }

    @GetMapping(path = "/api/order/{id}")
    public Order getOrder(@PathVariable Long id) {
        return service.findOne(id);
    }

    @GetMapping(path = "/api/order")
    public List<Order> listOrder() {
        return service.findAll();
    }

    private void validationData(List<OrderDetailDto> orderDetailDtos, List<OrderImage> orderImages) {
        if (!CommonUtils.isNull(orderDetailDtos) && orderDetailDtos.size() > 0) {
            int voucherCount = orderDetailDtos.stream().collect(Collectors.groupingBy(a -> a.getOrderId())).size();
            if (voucherCount > 1)
                throw new RuntimeException("orderId must be the same");
        }
        if (!CommonUtils.isNull(orderImages) && orderImages.size() > 0) {
            int voucherCount = orderImages.stream().collect(Collectors.groupingBy(a -> a.getOrder().getId())).size();
            if (voucherCount > 1)
                throw new RuntimeException("orderId must be the same");
        }
    }
}
