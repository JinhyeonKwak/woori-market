package com.mayy5.admin.model.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    private String street;
    private String detail;
    private String postCode;

    protected Address() {
    }

    public Address(String street, String detail, String postCode) {
        this.street = street;
        this.detail = detail;
        this.postCode = postCode;
    }
}
