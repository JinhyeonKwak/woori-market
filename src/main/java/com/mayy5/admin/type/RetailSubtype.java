package com.mayy5.admin.type;

import lombok.Getter;

@Getter
public enum RetailSubtype {

    TTEOKBOKKI("떡볶이"),
    JOKBAL("족발"),
    KIMBAP("김밥");

    private String krName;

    RetailSubtype(String krName) {
        this.krName = krName;
    }
}
