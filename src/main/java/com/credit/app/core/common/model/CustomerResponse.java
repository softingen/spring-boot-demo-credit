package com.credit.app.core.common.model;

import lombok.Data;

@Data
public class CustomerResponse extends BaseResponseModel {
    private CustomerInfo customerInfo;
}
