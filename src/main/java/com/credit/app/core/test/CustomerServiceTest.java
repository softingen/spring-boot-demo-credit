package com.credit.app.core.test;

import com.credit.app.core.SpringDemoApplication;
import com.credit.app.core.common.constants.Messages;
import com.credit.app.core.common.model.CustomerInfo;
import com.credit.app.core.common.model.CustomerResponse;
import com.credit.app.core.exception.AppServiceException;
import com.credit.app.core.service.impl.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDemoApplication.class)
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    public void saveCustomerWhenFullInfoTest() throws AppServiceException {
        CustomerInfo info = new CustomerInfo();
        info.setTckn("11111111111");
        info.setName("Name");
        info.setSurname("Surname");
        info.setEmail("asd@asd.com");
        info.setTelephone("343434445");
        info.setSalary(4000.0);

        CustomerResponse response = customerService.save(info);

        assertEquals(Messages.SAVE_SUCCESS, response.getMessage());
    }

    /**
     * This test will fail. Because validations are done at the bean level in the controller.
     * @throws AppServiceException
     */
    @Test
    public void saveCustomerWhenNameIsEmptyTest() throws AppServiceException {
        CustomerInfo info = new CustomerInfo();
        info.setTckn("11111111111");
        info.setSurname("Surname");
        info.setEmail("asd@asd.com");
        info.setTelephone("343434445");
        info.setSalary(4000.0);

        CustomerResponse response = customerService.save(info);

        assertEquals(Messages.NAME_MISSING, response.getMessage());
    }

    /**
     * This test will fail. Because validations are done at the bean level in the controller.
     * @throws AppServiceException
     */
    @Test
    public void saveCustomerWhenEMailIsEmptyTest() throws AppServiceException {
        CustomerInfo info = new CustomerInfo();
        info.setTckn("11111111111");
        info.setName("Name");
        info.setSurname("Surname");
        info.setEmail("asd@asd.com");
        info.setSalary(4000.0);

        CustomerResponse response = customerService.save(info);

        assertEquals(Messages.PHONE_MISSING, response.getMessage());
    }
}
