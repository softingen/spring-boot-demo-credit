package com.credit.app.core.ws;

import com.credit.app.core.common.model.CustomerInfo;
import com.credit.app.core.common.model.CustomerResponse;
import com.credit.app.core.common.ws.ServiceUri;
import com.credit.app.core.exception.AppServiceException;
import com.credit.app.core.persist.Customer;
import com.credit.app.core.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = ServiceUri.CUSTOMER)
public class CustomerController extends BaseController<Customer, CustomerInfo, ICustomerService> {

    @Autowired
    private ICustomerService service;

    @Override
    public ICustomerService getService() {
        return service;
    }

    @ResponseBody
    @RequestMapping(path = ServiceUri.LIST_CUSTOMERS, method = RequestMethod.GET)
    public List<CustomerInfo> listCustomer() throws AppServiceException {
        List<CustomerInfo> customers = null;
        try {
            customers = getService().getList();
        } catch (Exception ex) {
        } finally {
            return customers;
        }
    }

    @ResponseBody
    @RequestMapping(path = ServiceUri.ADD_CUSTOMER, method = RequestMethod.POST)
    public CustomerResponse addCustomer(@RequestBody CustomerInfo customer) throws AppServiceException {
        CustomerResponse result = new CustomerResponse();
        try {
            result = getService().save(customer);
        } catch (Exception ex) {
        } finally {
            return result;
        }
    }

}
