package com.credit.app.core.persist;

import com.credit.app.core.common.constants.Messages;
import com.credit.app.core.common.model.CustomerInfo;
import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;


@Data
@Entity
public class Customer extends BaseEntity<CustomerInfo> {
    private String tckn;
    private String email;
    private String name;
    private String surname;
    private double salary;
    private String telephone;
    private double creditLimit;

    @Override
    public CustomerInfo toInfo() {
        CustomerInfo i = new CustomerInfo();
        i.setCreatedDate(getCreatedDate());
        i.setId(getId());
        i.setUpdatedDate(getUpdatedDate());
        i.setName(getName());
        i.setSurname(getSurname());
        i.setTckn(getTckn());
        i.setEmail(getEmail());
        i.setSalary(getSalary());
        i.setTelephone(getTelephone());
        i.setCreditLimit(getCreditLimit());
        return i;
    }

    @Override
    public void fromInfo(CustomerInfo info) {
        setCreatedDate(info.getCreatedDate());
        setId(info.getId());
        setUpdatedDate(info.getUpdatedDate());
        setName(info.getName());
        setSurname(info.getSurname());
        setTckn(info.getTckn());
        setEmail(info.getEmail());
        setSalary(info.getSalary());
        setTelephone(info.getTelephone());
        setCreditLimit(info.getCreditLimit());
    }

}

