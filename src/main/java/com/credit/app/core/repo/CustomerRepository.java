package com.credit.app.core.repo;

import com.credit.app.core.common.model.CustomerInfo;
import com.credit.app.core.persist.Customer;

import java.util.List;

public interface CustomerRepository extends BaseRepository<Customer, CustomerInfo> {
    List<Customer> findByTckn(String tckn);
}
