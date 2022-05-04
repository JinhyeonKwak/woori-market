package com.mayy5.admin.controller;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MarketAgentForm {

    @NotEmpty(message = "회원 이름은 필수입니다.")
    private String userName;
    private String phoneNumber;
    private String corporateName;


}
