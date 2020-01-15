package com.credit.app.core.service.impl;

import com.credit.app.core.common.constants.CustomStatusCodes;
import com.credit.app.core.common.constants.Messages;
import com.credit.app.core.common.model.CustomerInfo;
import com.credit.app.core.common.model.CustomerResponse;
import com.credit.app.core.common.utils.Utils;
import com.credit.app.core.exception.AppServiceException;
import com.credit.app.core.persist.Customer;
import com.credit.app.core.repo.CustomerRepository;
import com.credit.app.core.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService extends BaseService<Customer, CustomerInfo> implements ICustomerService {

    @Autowired
    private CustomerRepository repo;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public CustomerRepository getRepo() {
        return repo;
    }

    @Override
    public Customer getEntity(CustomerInfo info) {
        return null;
    }

    @Override
    public Customer prepareForCreate(CustomerInfo info) throws AppServiceException {
//        validateFields(info);
//
//        List<User> list = getRepo().findByUsername(info.getUsername());
//
//        if (!list.isEmpty()) {
//            throw new AppServiceException("There is already a username as " + info.getUsername());
//        }

        Customer entity = new Customer();
        entity.fromInfo(info);
        return entity;
    }

    @Override
    public Customer prepareForUpdate(CustomerInfo info) throws AppServiceException {
        return null;
    }

    @Override
    public void prepareForDelete(Long id) throws AppServiceException {

    }

    @Override
    public CustomerResponse save(CustomerInfo info) throws AppServiceException {
        CustomerResponse customerResponse = new CustomerResponse();

        try {
            Customer customer = getByTckn(info.getTckn());

            if (customer == null) {
                int creditScore = Utils.newInstance().getCreditScore();
                if (creditScore < 500) {
                    info.setCreditLimit(0);
                } else if (creditScore < 1000) {
                    info.setCreditLimit(10000);
                } else {
                    info.setCreditLimit(info.getSalary() * 4);
                }

                CustomerInfo customerInfo = create(info);

                if (customerInfo != null) {
                    sendCreditScoreMail(info.getCreditLimit(), info.getEmail());

                    customerResponse.setCustomerInfo(customerInfo);
                    customerResponse.setMessage(Messages.SAVE_SUCCESS);
                    customerResponse.setStatusCode(CustomStatusCodes.SUCCESS);
                }

            }
        } catch (AppServiceException ex) {
        }

        return customerResponse;
    }

    @Override
    public Customer getByTckn(String tckn) throws AppServiceException {
        List<Customer> customers = getRepo().findByTckn(tckn);
        Customer customer = null;
        if (customers.size() != 0) {
            customer = getRepo().findByTckn(tckn).get(0);
        }
        return customer;
    }

    private boolean sendCreditScoreMail(long creditLimit, String email) {
        String message = "";
        if (creditLimit == 0) {
            message = Messages.CREDIT_ACCEPT;
        } else {
            message = Messages.CREDIT_REJECT + creditLimit + Messages.TL;
        }

        Utils utils = new Utils(javaMailSender);
        return utils.sendEmail(email, message);
    }

}
