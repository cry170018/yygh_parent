package com.xdu.yygh.hosp.service;

import com.xdu.yygh.model.hosp.Schedule;
import com.xdu.yygh.vo.hosp.ScheduleOrderVo;
import com.xdu.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ScheduleService {
    void save(Map<String, Object> paramMap);

    void remove(String hoscode, String hosScheduleId);

    Page<Schedule> findPageSchedule(int page, int limit, ScheduleQueryVo scheduleQueryVo);

    Map<String,Object> getRuleSchedule(long page, long limit, String hoscode, String depcode);

    List<Schedule> getDetailSchedule(String hoscode, String depcode, String workDate);

    Map<String, Object> getBookingScheduleRule(int page, int limit, String hoscode, String depcode);

    Object getById(String scheduleId);

    ScheduleOrderVo getScheduleOrderVo(String scheduleId);

    Schedule getScheduleId(String scheduleId);

    void update(Schedule  schedule);
}
