package com.mayy5.admin.model.dto;

import com.mayy5.admin.model.domain.Address;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class MarketDTO {

    private Address address;
    private LocalDate startDate;
    private LocalDate EndDate;
    private List<DayOfWeek> marketDay;

}
