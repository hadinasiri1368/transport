package org.transport.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.transport.common.CommonUtils;
import org.transport.common.MapperUtil;
import org.transport.dto.OrderDetailDto;
import org.transport.dto.OrderDto;
import org.transport.dto.Response.PriceDto;
import org.transport.dto.UserPersonDto;
import org.transport.model.*;
import org.transport.service.AuthenticationServiceProxy;
import org.transport.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
public class OrderAPI {
    @Autowired
    private OrderService service;
    @Autowired
    private AuthenticationServiceProxy authenticationServiceProxy;

    @PostMapping(path = "/transport/order/add")
    public Long addOrder(@RequestBody OrderDto orderDto, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.longValue(authenticationServiceProxy.getUserId(CommonUtils.getToken(request), uuid));
        Order order = MapperUtil.mapToOrder(orderDto);
        List <UserPersonDto> userPersonDtos = null;
        if (orderDto.getIsMyOrder()) {
            userPersonDtos = authenticationServiceProxy.listUserPersonByUserId(CommonUtils.getToken(request), null, null, userId).getContent();
            if (CommonUtils.isNull(userPersonDtos)) {
                throw new RuntimeException("2045");
            }
        }
        service.insert(order, userId, CommonUtils.isNull(userPersonDtos)? null:userPersonDtos.get(0));
        return order.getId();
    }

    @PostMapping(path = "/transport/order/edit")
    public Long editOrder(@RequestBody OrderDto orderDto, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.longValue(authenticationServiceProxy.getUserId(CommonUtils.getToken(request), uuid));
        Order order = MapperUtil.mapToOrder(orderDto);
        List <UserPersonDto> userPersonDtos = null;
        if (orderDto.getIsMyOrder()) {
            userPersonDtos = authenticationServiceProxy.listUserPersonByUserId(CommonUtils.getToken(request), null, null, userId).getContent();
            if (CommonUtils.isNull(userPersonDtos)) {
                throw new RuntimeException("2045");
            }
        }
        service.update(order, userId, CommonUtils.isNull(userPersonDtos)? null:userPersonDtos.get(0));
        return order.getId();
    }

    @PostMapping(path = "/transport/orderDetail/add")
    public Long addOrderDetail(@RequestBody List<OrderDetailDto> orderDetailDtos, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        validationData(orderDetailDtos, null);
        Long userId = CommonUtils.longValue(authenticationServiceProxy.getUserId(CommonUtils.getToken(request), uuid));
        List<OrderDetail> orderDetails = MapperUtil.mapToOrderDetail(orderDetailDtos);
        service.insert(orderDetailDtos.get(0).getOrderId(), orderDetails, userId);
        return orderDetailDtos.get(0).getOrderId();
    }

    @PostMapping(path = "/transport/orderImage/add", consumes = {"multipart/form-data"})
    public Long addOrderImage(@RequestParam("orderId") Long orderId, @RequestParam("image") MultipartFile[] multipartFiles, HttpServletRequest request) {
        try {
            String uuid = request.getHeader("X-UUID");
            Long userId = CommonUtils.longValue(authenticationServiceProxy.getUserId(CommonUtils.getToken(request), uuid));
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

    @DeleteMapping(path = "/transport/order/remove/{id}")
    public Long removeOrder(@PathVariable Long id) {
        return (long) service.delete(id);
    }

    @GetMapping(path = "/transport/order/{id}")
    public Order getOrder(@PathVariable Long id) {
        return service.findOne(id);
    }

    @GetMapping(path = "/transport/order")
    public Page<Order> listOrder(HttpServletRequest request, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.longValue(authenticationServiceProxy.getUserId(CommonUtils.getToken(request), uuid));
        String token = CommonUtils.getToken(request);
        return service.findAll(userId, token, uuid, page, size);
    }

    @PostMapping(path = "/transport/acceptOrderCarDriver")
    public Long acceptOrderCarDriver(@ModelAttribute("orderId") Long orderId, @ModelAttribute("carId") Long carId, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request), uuid);
        service.acceptOrderCarDriver(orderId, carId, userId, CommonUtils.getToken(request), uuid);
        return orderId;
    }

    @PostMapping(path = "/transport/changeOrderStatus")
    public Long changeOrderStatus(@ModelAttribute("orderId") Long orderId, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request), uuid);
        service.changeOrderStatus(orderId, userId, CommonUtils.getToken(request), uuid);
        return orderId;
    }

    @PostMapping(path = "/transport/cancelledOrder")
    public Long cancelledOrder(@ModelAttribute("orderId") Long orderId, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.getUserId(CommonUtils.getToken(request), uuid);
        service.cancelledOrder(orderId, userId, CommonUtils.getToken(request), uuid);
        return orderId;
    }

    private void validationData(List<OrderDetailDto> orderDetailDtos, List<OrderImage> orderImages) {
        if (!CommonUtils.isNull(orderDetailDtos) && !orderDetailDtos.isEmpty()) {
            int voucherCount = orderDetailDtos.stream().collect(Collectors.groupingBy(OrderDetailDto::getOrderId)).size();
            if (voucherCount > 1)
                throw new RuntimeException("2005");
        }
        if (!CommonUtils.isNull(orderImages) && !orderImages.isEmpty()) {
            int voucherCount = orderImages.stream().collect(Collectors.groupingBy(OrderImage::getOrderId)).size();
            if (voucherCount > 1)
                throw new RuntimeException("2005");
        }
    }


    @PostMapping(path = "/transport/calculationPrice")
    public ResponseEntity<?> calculationPrice(@RequestParam Long orderId, @RequestParam(required = false) Long companyId, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        String token = CommonUtils.getToken(request);
        List<PriceDto> priceDtos = service.calculationPrice(orderId, companyId, token, uuid);
        return ResponseEntity.ok(priceDtos);
    }

}
