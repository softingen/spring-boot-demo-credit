package com.credit.app.core.common.model;

import lombok.Data;

@Data
public class CustomerInfo extends BaseInfo {
    private String tckn;
    private String email;
    private String name;
    private String surname;
    private long salary;
    private String telephone;
    private long creditLimit;
}
