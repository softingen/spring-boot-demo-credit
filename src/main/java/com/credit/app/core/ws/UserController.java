package com.credit.app.core.ws;

import com.credit.app.core.common.constants.CustomStatusCodes;
import com.credit.app.core.common.model.UserInfo;
import com.credit.app.core.common.ws.ServiceUri;
import com.credit.app.core.config.AuthResp;
import com.credit.app.core.exception.AppServiceException;
import com.credit.app.core.persist.User;
import com.credit.app.core.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping(path = ServiceUri.USER)
public class UserController extends BaseController<User, UserInfo, IUserService> {

    @Autowired
    private IUserService service;

    @Override
    public IUserService getService() {
        return service;
    }

    @ResponseBody
    @RequestMapping(path = ServiceUri.SIGNUP, method = RequestMethod.POST)
    public AuthResp signup(@RequestBody UserInfo info) throws AppServiceException {
        String token = getService().signup(info);
        return new AuthResp(token);
    }

    @ResponseBody
    @RequestMapping(path = ServiceUri.LOGIN, method = RequestMethod.POST)
    public AuthResp login(@RequestBody UserInfo info) throws AppServiceException {
        try {
            String token = getService().login(info);
            return new AuthResp(token, CustomStatusCodes.SUCCESS, "Successfully Login");
        } catch (Exception ex) {
            return new AuthResp("", CustomStatusCodes.FAILURE, "Invalid Username or Password");
        }
    }

    @ResponseBody
    @RequestMapping(path = ServiceUri.LIST_ID, method = RequestMethod.GET)
    List<UserInfo> getListByStatus(@PathVariable Integer id) throws AppServiceException {
        return getService().getListByStatus(id);
    }

}
