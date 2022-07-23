package com.mayy5.admin.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {

    private String roadAddress;
    private String jibunAddress;
    private String regionCode;
    private String latitude;
    private String longitude;

}
