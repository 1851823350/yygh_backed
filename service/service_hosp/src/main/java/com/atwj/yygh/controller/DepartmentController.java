package com.atwj.yygh.controller;

import com.atwj.yygh.commonResult.Result;
import com.atwj.yygh.model.hosp.Department;
import com.atwj.yygh.service.DepartmentService;
import com.atwj.yygh.vo.hosp.DepartmentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 吴先森
 * @description:
 * @create 2022-10-07 8:22
 */
@RestController
@RequestMapping("/admin/hosp/department")
@Api(tags = "科室管理接口")
//@CrossOrigin
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;

    //根据医院编号，查询此编号医院下的全部科室
    @ApiOperation(value = "根据编号查询医院科室")
    @GetMapping("getDeptList/{hoscode}")
    public Result getDeptList(@PathVariable String hoscode){
        List<DepartmentVo> deptList = departmentService.findDeptTree(hoscode);
        return Result.ok(deptList);
    }
}
