package org.transport.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.transport.common.CommonUtils;
import org.transport.common.MapperUtil;
import org.transport.dto.OrderDetailDto;
import org.transport.dto.OrderDto;
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


    @Operation(summary = "ثبت سفارش حمل")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "سفارش ثبت شد")})
    @PostMapping(path = "/transport/order/add")
    public Long addOrder(@RequestBody OrderDto orderDto, HttpServletRequest request) throws Exception {
        String uuid = request.getHeader("X-UUID");
        Long userId = CommonUtils.longValue(authenticationServiceProxy.getUserId(CommonUtils.getToken(request), uuid));
        Order order = MapperUtil.mapToOrder(orderDto);
        service.insert(order, userId);
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
        service.delete(id);
        return id;
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
        if (!CommonUtils.isNull(orderDetailDtos) && orderDetailDtos.size() > 0) {
            int voucherCount = orderDetailDtos.stream().collect(Collectors.groupingBy(a -> a.getOrderId())).size();
            if (voucherCount > 1)
                throw new RuntimeException("2005");
        }
        if (!CommonUtils.isNull(orderImages) && orderImages.size() > 0) {
            int voucherCount = orderImages.stream().collect(Collectors.groupingBy(a -> a.getOrderId())).size();
            if (voucherCount > 1)
                throw new RuntimeException("2005");
        }
    }
}
