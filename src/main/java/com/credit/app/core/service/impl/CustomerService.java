package com.credit.app.core.service.impl;

import com.credit.app.core.client.CreditPointClient;
import com.credit.app.core.common.constants.Constants;
import com.credit.app.core.common.constants.CustomStatusCodes;
import com.credit.app.core.common.constants.Messages;
import com.credit.app.core.common.model.CustomerInfo;
import com.credit.app.core.common.model.CustomerResponse;
import com.credit.app.core.exception.AppServiceException;
import com.credit.app.core.persist.Customer;
import com.credit.app.core.repo.CustomerRepository;
import com.credit.app.core.service.ICustomerService;
import com.credit.app.core.util.CreditLimitCalculator;
import com.credit.app.core.util.GeneralUtils;
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

    @Autowired
    private CreditPointClient creditPointClient;

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
                String creditPoint = "0";
                try {
                    creditPoint = creditPointClient.getCreditPoint();
                    creditPoint = creditPoint.replace(Constants.SEPARATOR, ""); //Service send response with line seperator
                } catch (Exception e) {
                    creditPoint = "" + GeneralUtils.generateRandom(0, 2000);
                }

                Double creditLimit = CreditLimitCalculator.calculateCreditLimit(Integer.parseInt(creditPoint), info.getSalary());
                info.setCreditLimit(creditLimit);

                // Create Customer
                CustomerInfo customerInfo = create(info);

                if (customerInfo != null) {
                    // You can uncomment this line after entering the mail settings in the properties file that shown below properly.
                    // spring.mail.host=
                    // spring.mail.port=
                    // spring.mail.username=
                    // spring.mail.password=
                    sendCreditScoreMail(info.getCreditLimit(), info.getEmail());

                    customerResponse.setCustomerInfo(customerInfo);
                    customerResponse.setMessage(Messages.SAVE_SUCCESS);
                    customerResponse.setStatusCode(CustomStatusCodes.SUCCESS);
                }

            }
        } catch (AppServiceException ex) {
            customerResponse.setMessage(ex.getMessage());
            customerResponse.setStatusCode(CustomStatusCodes.FAILURE);
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

    private boolean sendCreditScoreMail(double creditLimit, String email) {
        String message = "";
        if (creditLimit == 0) {
            message = Messages.CREDIT_ACCEPT;
        } else {
            message = Messages.CREDIT_REJECT + creditLimit + Messages.TL;
        }

        GeneralUtils utils = new GeneralUtils(javaMailSender);
        return utils.sendEmail(email, message);
    }
}
