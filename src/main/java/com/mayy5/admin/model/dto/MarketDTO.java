package com.mayy5.admin.model.dto;

import com.mayy5.admin.model.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class MarketDTO {

    private Address address;
    private LocalDate startDate;
    private LocalDate EndDate;
    private DayOfWeek marketDay;

}
