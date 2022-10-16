package com.atwj.yygh.service;

import com.atwj.yygh.model.hosp.Schedule;
import com.atwj.yygh.vo.hosp.ScheduleOrderVo;
import com.atwj.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 吴先森
 * @description: 排班服务接口
 * @create 2022-10-04 10:27
 */
public interface ScheduleService {
    //添加、修改排班
    void save(Map<String, Object> paramMap);

    //分页查询排班信息
    Page<Schedule> findPageSchedule(int page, int limit, ScheduleQueryVo scheduleQueryVo);

    //删除排班信息
    void remove(String hoscode, String hosScheduleId);

    //查询排班规则
    Map<String, Object> getRuleSchedule(long page, long limit, String hoscode, String depcode);

    //根据医院编号、科室编号、工作日期查询排班信息
    List<Schedule> getDetailSchedule(String hoscode, String depcode, String workDate);

    //获取排班可预约日期数据
    Map<String, Object> getBookingScheduleRule(Integer page, Integer limit, String hoscode, String depcode);

    //根据scheduleId查找预约信息
    Schedule  getById(String scheduleId);

    //根据排班id获取预约下单数据
    ScheduleOrderVo getScheduleOrderVo(String scheduleId);

    //更新排班数据 用于mp
    void update(Schedule schedule);
}
