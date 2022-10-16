package com.atwj.yygh.repository;

import com.atwj.yygh.model.hosp.Department;
import com.atwj.yygh.model.hosp.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

/**
 * @author 吴先森
 * @description: 操纵mongodb数据库
 * @create 2022-10-03 13:17
 */
public interface ScheduleRepository extends MongoRepository<Schedule,String> {

    Schedule getScheduleByHoscodeAndHosScheduleId(String hoscode, String hosScheduleId);

    List<Schedule> getScheduleByHoscodeAndDepcodeAndWorkDate(String hoscode, String depcode, Date workDate);
}
