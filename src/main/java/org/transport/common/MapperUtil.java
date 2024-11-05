package org.transport.common;

import org.springframework.stereotype.Component;
import org.transport.dto.OrderDetailDto;
import org.transport.dto.OrderDto;
import org.transport.dto.Response.*;
import org.transport.dto.VoucherDetailDto;
import org.transport.model.*;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapperUtil {

    public static VoucherDetail mapToVoucherDetail(VoucherDetailDto voucherDetailDto) {
        VoucherDetail voucherDetail = ObjectMapperUtils.map(voucherDetailDto, VoucherDetail.class);
        voucherDetail.setDebitAmount(CommonUtils.isNull(voucherDetail.getDebitAmount()) ? 0 : voucherDetail.getDebitAmount());
        voucherDetail.setCreditAmount(CommonUtils.isNull(voucherDetail.getCreditAmount()) ? 0 : voucherDetail.getCreditAmount());
        voucherDetail.setSubsidiaryLedger(new SubsidiaryLedger(voucherDetailDto.getSubsidiaryLedgerId(), null, null, null));
        voucherDetail.setDetailLedger(new DetailLedger(voucherDetailDto.getDetailLedgerId(), null, null, null));
        voucherDetail.setVoucher(new Voucher(voucherDetailDto.getVoucherId(), null, null, null));
        return voucherDetail;
    }

    public static List<VoucherDetail> mapToVoucherDetail(List<VoucherDetailDto> voucherDetailDtos) {
        return voucherDetailDtos.stream()
                .map(entity -> mapToVoucherDetail(entity))
                .collect(Collectors.toList());
    }

    public static OrderDetail mapToOrderDetail(OrderDetailDto orderDetailDto) {
        OrderDetail orderDetail = ObjectMapperUtils.map(orderDetailDto, OrderDetail.class);
        orderDetail.setOrder(Order.builder().id(orderDetailDto.getOrderId()).build());
        return orderDetail;
    }

    public static List<OrderDetail> mapToOrderDetail(List<OrderDetailDto> orderDetailDtos) {
        return orderDetailDtos.stream()
                .map(entity -> mapToOrderDetail(entity))
                .collect(Collectors.toList());
    }

    public static Order mapToOrder(OrderDto orderDto) {
        Order order = ObjectMapperUtils.map(orderDto, Order.class);
        return order;
    }

    public static List<Order> mapToOrder(List<OrderDto> orderDtos) {
        return orderDtos.stream()
                .map(entity -> mapToOrder(entity))
                .collect(Collectors.toList());
    }

    public static OrderDto mapToOrderDto(Order order, List<OrderStatusDto> orderStatusDtos,
                                         List<CarTypeDto> carTypeDtos) {
        OrderDto orderDto = ObjectMapperUtils.map(order, OrderDto.class);
        if (orderDto != null) {
            orderDto.setOrderStatusName(
                    orderStatusDtos.stream()
                            .filter(status -> status.getId().equals(order.getOrderStatusId()))
                            .map(OrderStatusDto::getName)
                            .findFirst()
                            .orElse(null)
            );
            orderDto.setCarTypeName(
                    carTypeDtos.stream()
                            .filter(carType -> carType.getId().equals(order.getCarTypeId()))
                            .map(CarTypeDto::getName)
                            .findFirst()
                            .orElse(null)
            );

        }
        return orderDto;
    }

    public static List<OrderDetailDto> mapToOrderDetailDto(List<OrderDetail> orderDetails, List<BaseInfoGoodDto> baseInfoGoodDtos, List<PackingTypeDto> packingTypeDtos, List<LoadingTypeDto> loadingTypeDtos) {
        return orderDetails.stream()
                .map(orderDetail -> {
                    OrderDetailDto orderDetailDto = mapToOrderDetailDto(orderDetail);

                    String baseInfoGoodName = baseInfoGoodDtos.stream()
                            .filter(baseInfoGoodDto -> baseInfoGoodDto.getId().equals(orderDetail.getBaseInfoGoodId()))
                            .map(BaseInfoGoodDto::getName)
                            .findFirst()
                            .orElse(null);

                    String packingTypeName = packingTypeDtos.stream()
                            .filter(packingTypeDto -> packingTypeDto.getId().equals(orderDetail.getPackingTypeId()))
                            .map(PackingTypeDto::getName)
                            .findFirst().orElse(null);

                    String loadingTypeName = loadingTypeDtos.stream()
                            .filter(loadingTypeDto -> loadingTypeDto.getId().equals(orderDetail.getLoadingTypeId()))
                            .map(LoadingTypeDto::getName)
                            .findFirst().orElse(null);
                    orderDetailDto.setBaseInfoGoodName(baseInfoGoodName);
                    orderDetailDto.setPackingTypeName(packingTypeName);
                    orderDetailDto.setLoadingTypeName(loadingTypeName);
                    return orderDetailDto;
                })
                .collect(Collectors.toList());
    }

    public static OrderDetailDto mapToOrderDetailDto(OrderDetail orderDetail) {
        OrderDetailDto orderDetailDto = ObjectMapperUtils.map(orderDetail, OrderDetailDto.class);
        orderDetailDto.setOrderId(orderDetail.getOrder().getId());
        return orderDetailDto;
    }
}
