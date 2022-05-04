package com.mayy5.admin.model.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Schedule {

    @Id @GeneratedValue
    @Column(name = "SCHEDULE_ID")
    private Long id;

    @OneToOne(mappedBy = "schedule", fetch = FetchType.LAZY)
    private Market market;

    @OneToMany(mappedBy = "schedule")
    private List<RetailerSchedule> retailerScheduleList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Attend attend; // ATTEND, NO_SHOW

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

     /*
     schedule의 시작 시각과 갱신 시각을 설정하는 로직 필요
     장의 운영 방식을 먼저 알아야 함
     */

    //==연관 관계 메서드==//
    public void addRetailerSchedule(RetailerSchedule retailerSchedule) {
        retailerScheduleList.add(retailerSchedule);
        retailerSchedule.setSchedule(this);
    }

}
