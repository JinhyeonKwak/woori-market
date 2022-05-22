package com.mayy5.admin.model.dto;

import com.mayy5.admin.model.domain.Address;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarketDTO {

    private Address address;
    private LocalDate startDate;
    private LocalDate endDate;
    private DayOfWeek marketDay;
}
