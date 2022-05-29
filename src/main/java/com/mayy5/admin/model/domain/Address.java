package com.mayy5.admin.model.domain;

import javax.persistence.Embeddable;

import lombok.Getter;

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
