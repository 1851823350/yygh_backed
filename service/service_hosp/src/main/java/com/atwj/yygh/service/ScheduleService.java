package com.atwj.yygh.service;

import com.atwj.yygh.model.hosp.Department;
import com.atwj.yygh.model.hosp.Schedule;
import com.atwj.yygh.vo.hosp.DepartmentQueryVo;
import com.atwj.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;

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
}
