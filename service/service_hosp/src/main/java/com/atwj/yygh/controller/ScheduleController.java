package com.atwj.yygh.controller;

import com.atwj.yygh.commonResult.Result;
import com.atwj.yygh.model.hosp.Schedule;
import com.atwj.yygh.service.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 吴先森
 * @description:
 * @create 2022-10-07 14:28
 */
@RestController
@RequestMapping("/admin/hosp/schedule")
@Api(tags = "排班信息接口")
//@CrossOrigin
public class ScheduleController {

    @Resource
    private ScheduleService scheduleService;

    //根据医院编号、科室编号查询排班数据
    @ApiOperation(value = "查询排班数据")
    @GetMapping("getScheduleRule/{page}/{limit}/{hoscode}/{depcode}")
    public Result getScheduleRule(@PathVariable long page,
                                  @PathVariable long limit,
                                  @PathVariable String hoscode,
                                  @PathVariable String depcode){
        Map<String, Object> map = scheduleService.getRuleSchedule(page, limit, hoscode, depcode);
        return Result.ok(map);
    }

    //根据医院编号、科室编号、工作日期查询排班信息
    @ApiOperation(value = "查询排班信息")
    @GetMapping("getScheduleDetail/{hoscode}/{depcode}/{workDate}")
    public Result getScheduleDetail(@PathVariable String hoscode,
                                    @PathVariable String depcode,
                                    @PathVariable String workDate){
        List<Schedule> list = scheduleService.getDetailSchedule(hoscode, depcode, workDate);
        return Result.ok(list);
    }

}
