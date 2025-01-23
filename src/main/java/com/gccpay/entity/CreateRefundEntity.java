package com.gccpay.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRefundEntity {

    private String merCode;
    private String merRefundOrderId;
    private String origOrderId;
    private String origMerOrderId;
    private BigDecimal refundAmt;
    private String refundCurrency;
    private String refundMemo;
}
