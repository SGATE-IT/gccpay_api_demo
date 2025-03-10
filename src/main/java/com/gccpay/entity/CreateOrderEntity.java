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

    private String merchantId;
    private String merOrderId;
    private String subMerId;
    private String orderType;
    private String paymentType;
    private BigDecimal orderAmt;
    private String orderCurrency;
    private long expireTime;
    private String frontRedirectUrl;
    private List<ProductInfo> productInfo;
    private CustomerInfo customerInfo=new CustomerInfo();
    private DeliverInfo deliveryInfo=new DeliverInfo();
    private String orderDesc;
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductInfo {
        private String productType;
        private String productName;
        private String productSku;
        private Integer productQuantity;
        private BigDecimal productUnitPrice;
        private String productCurrency;
        private String productDesc;
        private String productAvatarUrl;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerInfo {
        private String customerUid;
        private String customerName;
        private String customerPhone;
        private String customerEmail;
        private String customerPlatform;

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
