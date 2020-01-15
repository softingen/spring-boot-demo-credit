package com.credit.app.core.service;

import com.credit.app.core.common.model.CustomerInfo;
import com.credit.app.core.common.model.CustomerResponse;
import com.credit.app.core.exception.AppServiceException;
import com.credit.app.core.persist.Customer;

import java.util.List;

public interface ICustomerService extends IBaseService<Customer, CustomerInfo> {

    Customer getByTckn(String tckn) throws AppServiceException;

    CustomerResponse save(CustomerInfo info) throws AppServiceException;

    List<CustomerInfo> getList() throws AppServiceException;

}
