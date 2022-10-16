package com.atwj.yygh.controller;

import com.atwj.yygh.commonResult.Result;
import com.atwj.yygh.model.hosp.Hospital;
import com.atwj.yygh.service.HospitalService;
import com.atwj.yygh.vo.hosp.HospitalQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 吴先森
 * @description: 医院管理接口，
 * @create 2022-10-04 15:08
 */
@Api(tags = "医院管理接口")
@RestController
@RequestMapping("/admin/hosp/hospital")
//@CrossOrigin(allowCredentials = "true") //解决跨域问题
public class HospitalController {
    @Resource
    private HospitalService hospitalService;

    //医院列表（分页查询医院详细信息）
    @GetMapping("list/{page}/{limit}")
    public Result listHosp(@PathVariable Integer page,
                           @PathVariable Integer limit,
                           HospitalQueryVo hospitalQueryVo) {
        Page<Hospital> pageModel = hospitalService.selectPage(page, limit, hospitalQueryVo);

        return Result.ok(pageModel);
    }

    //更改医院上线状态
    @ApiOperation(value = "更新上线状态")
    @GetMapping("updateHospStatus/{id}/{status}")
    public Result lock(
            @ApiParam(name = "id", value = "医院id", required = true)
            @PathVariable("id") String id,
            @ApiParam(name = "status", value = "状态（0：未上线 1：已上线）", required = true)
            @PathVariable("status") Integer status) {
        hospitalService.updateStatus(id, status);
        return Result.ok();
    }

    //查看医院详细信息
    @ApiOperation(value = "查看医院详细信息")
    @GetMapping("showHospDetail/{id}")
    public Result showHospDelete(@PathVariable String id){
        Map<String, Object> finalMap = hospitalService.getHospById(id);
        return Result.ok(finalMap);
    }
}