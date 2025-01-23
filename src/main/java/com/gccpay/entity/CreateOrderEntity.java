package com.gccpay.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderEntity {

    private String merCode;
    private String merOrderId;
    private String subMerCode;
    private String tranCode;
    private String payCode;
    private BigDecimal tradeAmt;
    private String tradeCurrency;
    private long expireTime;
    private String callbackFrontUrl;
    private List<ProductInfo> productInfo;
    private CustomerInfo customerInfo=new CustomerInfo();
    private DeliverInfo deliverInfo=new DeliverInfo();
    private String orderMemo;
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductInfo {
        private String productType;
        private String productName;
        private String productSku;
        private Integer productQuantity;
        private BigDecimal productPrice;
        private String productCurrency;
        private String productDescription;
        private String photoUrl;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerInfo {
        private String customerUid;
        private String customerName;
        private String customerPhone;
        private String customerEmail;
        private String customerPlateform;

    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeliverInfo {
        private String firstName;
        private String lastName;
        private String phone;
        private String country;
        private String city;
        private String address;
        private String zipCode;

    }
}
