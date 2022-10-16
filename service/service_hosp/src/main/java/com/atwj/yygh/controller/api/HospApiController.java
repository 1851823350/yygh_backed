package com.atwj.yygh.controller.api;

import com.atwj.yygh.commonResult.Result;
import com.atwj.yygh.model.hosp.Department;
import com.atwj.yygh.model.hosp.Hospital;
import com.atwj.yygh.service.DepartmentService;
import com.atwj.yygh.service.HospitalService;
import com.atwj.yygh.service.HospitalSetService;
import com.atwj.yygh.service.ScheduleService;
import com.atwj.yygh.vo.hosp.DepartmentVo;
import com.atwj.yygh.vo.hosp.HospitalQueryVo;
import com.atwj.yygh.vo.hosp.ScheduleOrderVo;
import com.atwj.yygh.vo.order.SignInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 吴先森
 * @description:
 * @create 2022-10-08 12:06
 */
@RestController
@Api(tags = "预约平台-前台界面医院查询接口")
@RequestMapping("/api/hosp/hospital")
public class HospApiController {
    @Resource
    private HospitalService hospitalService;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private ScheduleService scheduleService;
    @Resource
    private HospitalSetService hospitalSetService;

    @ApiOperation(value = "预约平台首页下方数据-分页查询医院列表")
    @GetMapping("findHospList/{page}/{limit}")
    public Result findHospList(@PathVariable Integer page,
                               @PathVariable Integer limit,
                               HospitalQueryVo hospitalQueryVo){
        Page<Hospital> result = hospitalService.selectPage(page, limit, hospitalQueryVo);
        return Result.ok(result);
    }

    @ApiOperation(value = "预约平台搜索栏-根据医院名称查询数据")
    @GetMapping("findByHosname/{hosname}")
    public Result findByHosName(@PathVariable String hosname){
        List<Hospital> resultList = hospitalService.findHospByHosname(hosname);
        return Result.ok(resultList);
    }

    @ApiOperation(value = "预约平台-根据医院编号查询科室")
    @GetMapping("department/{hoscode}")
    public Result index(@PathVariable String hoscode){
        List<DepartmentVo> departmentList = departmentService.findDeptTree(hoscode);
        return Result.ok(departmentList);
    }

    @ApiOperation(value = "预约平台-根据医院编号查询预约挂号详情")
    @GetMapping("findHospDetail/{hoscode}")
    public Result item(@PathVariable String hoscode){
        Map<String, Object> map = hospitalService.item(hoscode);
        return Result.ok(map);
    }


    @ApiOperation(value = "获取可预约排班数据")
    @GetMapping("auth/getBookingScheduleRule/{page}/{limit}/{hoscode}/{depcode}")
    public Result getBookingSchedule(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Integer page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Integer limit,
            @ApiParam(name = "hoscode", value = "医院code", required = true)
            @PathVariable String hoscode,
            @ApiParam(name = "depcode", value = "科室code", required = true)
            @PathVariable String depcode) {
        return Result.ok(scheduleService.getBookingScheduleRule(page, limit, hoscode, depcode));
    }

    @ApiOperation(value = "获取排班数据")
    @GetMapping("auth/findScheduleList/{hoscode}/{depcode}/{workDate}")
    public Result findScheduleList(
            @ApiParam(name = "hoscode", value = "医院code", required = true)
            @PathVariable String hoscode,
            @ApiParam(name = "depcode", value = "科室code", required = true)
            @PathVariable String depcode,
            @ApiParam(name = "workDate", value = "排班日期", required = true)
            @PathVariable String workDate) {
        return Result.ok(scheduleService.getDetailSchedule(hoscode, depcode, workDate));
    }

    @ApiOperation(value = "根据排班id获取排班数据")
    @GetMapping("getSchedule/{scheduleId}")
    public Result getSchedule(
            @ApiParam(name = "scheduleId", value = "排班id", required = true)
            @PathVariable String scheduleId) {
        return Result.ok(scheduleService.getById(scheduleId));
    }

    @ApiOperation(value = "根据排班id获取预约下单数据")
    @GetMapping("inner/getScheduleOrderVo/{scheduleId}")
    public ScheduleOrderVo getScheduleOrderVo(
            @ApiParam(name = "scheduleId", value = "排班id", required = true)
            @PathVariable("scheduleId") String scheduleId) {
        return scheduleService.getScheduleOrderVo(scheduleId);
    }

    @ApiOperation(value = "获取医院签名信息")
    @GetMapping("inner/getSignInfoVo/{hoscode}")
    public SignInfoVo getSignInfoVo(
            @ApiParam(name = "hoscode", value = "医院code", required = true)
            @PathVariable("hoscode") String hoscode) {
        return hospitalSetService.getSignInfoVo(hoscode);
    }
}
