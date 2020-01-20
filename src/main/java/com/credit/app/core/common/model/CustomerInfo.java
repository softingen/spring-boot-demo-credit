package com.credit.app.core.common.model;

import com.credit.app.core.common.constants.Messages;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class CustomerInfo extends BaseInfo {
    @Size(min = 11, max = 11, message = Messages.TCKN_FORMAT_ERROR)
    @NotBlank (message = Messages.TCKN_MISSING)
    private String tckn;

    @Email(message = Messages.EMAIL_FORMAT_ERROR)
    @NotBlank (message = Messages.EMAIL_MISSING)
    private String email;

    @NotBlank (message = Messages.NAME_MISSING)
    private String name;

    @NotBlank (message = Messages.SURNAME_MISSING)
    private String surname;

    @Min(value = 2340, message = Messages.SALARY_ERROR)
    @NotNull(message = Messages.INCOME_MISSING)
    private double salary;

    @Pattern(regexp ="[0-9\\s]{12}", message = Messages.PHONE_FORMAT_ERROR)
    @NotBlank (message = Messages.PHONE_MISSING)
    private String telephone;

    private double creditLimit;
}
