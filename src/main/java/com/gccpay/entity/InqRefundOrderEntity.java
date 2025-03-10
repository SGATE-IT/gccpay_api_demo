package com.gccpay.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InqRefundOrderEntity {
    private String merchantId;
    private String merRefundOrderId;
    private String refundOrderId;
}
