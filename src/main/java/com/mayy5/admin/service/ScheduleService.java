package com.mayy5.admin.service;

import com.mayy5.admin.model.domain.Schedule;
import com.mayy5.admin.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public Long create(Schedule schedule) {
        scheduleRepository.save(schedule);
        return schedule.getId();
    }

    @Transactional(readOnly = true)
    public Schedule findOne(Long scheduleId) {
        return scheduleRepository.findOne(scheduleId);
    }

    @Transactional(readOnly = true)
    public List<Schedule> findSchedules() {
        return scheduleRepository.findAll();
    }
}
