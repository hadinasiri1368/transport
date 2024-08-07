package org.transport.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.transport.dto.OrderDetailDto;
import org.transport.dto.OrderDto;
import org.transport.dto.VoucherDetailDto;
import org.transport.model.*;
import org.transport.service.GenericService;

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
}
