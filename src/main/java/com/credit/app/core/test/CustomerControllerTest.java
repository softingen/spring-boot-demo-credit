package com.credit.app.core.test;

import com.credit.app.core.common.constants.Messages;
import com.credit.app.core.common.model.CustomerInfo;
import com.credit.app.core.common.model.UserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class CustomerControllerTest extends AbstractMvcTest {

    public String getAuthToken() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("system");
        userInfo.setPassword("system");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(userInfo);

        MvcResult mvcResult = mockMvc.perform(post("/user/login").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String token = extractToken(mvcResult);

        return token;
    }

    @Test
    public void userAuthTest() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("system");
        userInfo.setPassword("system");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        String requestJson = ow.writeValueAsString(userInfo);

        MvcResult mvcResult = mockMvc.perform(post("/user/login").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String token = extractToken(mvcResult);

        Assert.assertNotNull(token);
    }

    @Test
    public void saveCustomerNameIsEmpty() throws Exception {
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setTckn("11111112145");
        customerInfo.setSurname("Surname");
        customerInfo.setEmail("emrahgunx@gmail.com");
        customerInfo.setTelephone("123456789123");
        customerInfo.setSalary(4000.0);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        String token = getAuthToken();

        String addCustomerRequestJson = ow.writeValueAsString(customerInfo);
        MvcResult mvcAddCustomerRes = mockMvc.perform(post("/customer/add-customer").content(addCustomerRequestJson).contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
                .andReturn();

        Assert.assertEquals(Messages.NAME_MISSING, extractMessage(mvcAddCustomerRes));
    }

    @Test
    public void saveCustomerTCKNValidation() throws Exception {
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setTckn("123456789123545845");
        customerInfo.setName("Name");
        customerInfo.setSurname("Surname");
        customerInfo.setEmail("emrahgunx@gmail.com");
        customerInfo.setTelephone("123456789123");
        customerInfo.setSalary(4000.0);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        String token = getAuthToken();

        String addCustomerRequestJson = ow.writeValueAsString(customerInfo);
        MvcResult mvcAddCustomerRes = mockMvc.perform(post("/customer/add-customer").content(addCustomerRequestJson).contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
                .andReturn();

        Assert.assertEquals(Messages.TCKN_FORMAT_ERROR, extractMessage(mvcAddCustomerRes));
    }

    @Test
    public void saveCustomerSurnameIsEmpty() throws Exception {
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setTckn("11112321456");
        customerInfo.setName("Name");
        customerInfo.setEmail("emrahgunx@gmail.com");
        customerInfo.setTelephone("123456789123");
        customerInfo.setSalary(4000.0);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        String token = getAuthToken();

        String addCustomerRequestJson = ow.writeValueAsString(customerInfo);
        MvcResult mvcAddCustomerRes = mockMvc.perform(post("/customer/add-customer").content(addCustomerRequestJson).contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
                .andReturn();

        Assert.assertEquals(Messages.SURNAME_MISSING, extractMessage(mvcAddCustomerRes));
    }

    @Test
    public void saveCustomerInvalidEMail() throws Exception {
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setTckn("11112321456");
        customerInfo.setName("Name");
        customerInfo.setSurname("Surname");
        customerInfo.setEmail("emrahgunxgmail.com");
        customerInfo.setTelephone("123456789123");
        customerInfo.setSalary(4000.0);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        String token = getAuthToken();

        String addCustomerRequestJson = ow.writeValueAsString(customerInfo);
        MvcResult mvcAddCustomerRes = mockMvc.perform(post("/customer/add-customer").content(addCustomerRequestJson).contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
                .andReturn();

        Assert.assertEquals(Messages.EMAIL_FORMAT_ERROR, extractMessage(mvcAddCustomerRes));
    }
}
