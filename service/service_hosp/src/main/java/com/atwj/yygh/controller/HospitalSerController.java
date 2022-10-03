package com.atwj.yygh.controller;


import com.atwj.yygh.commonResult.Result;
import com.atwj.yygh.model.hosp.HospitalSet;
import com.atwj.yygh.service.HospitalSetService;
import com.atwj.yygh.vo.hosp.HospitalSetQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 吴先森
 * @description:
 * @create 2022-09-27 8:32
 */
@Api(tags = "医院设置管理")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
@CrossOrigin //跨域
public class HospitalSerController {

    @Resource
    private HospitalSetService hospitalSetService;

    //查询医院所有的信息
    @ApiOperation(value = "获取医院所有信息")
    @GetMapping("/findAll")
    public Result findAllHospitalSet(){
        List<HospitalSet> ResultList = hospitalSetService.list();
        return Result.ok(ResultList);
    }

    //根据id逻辑删除医院信息，is_delete设置为1
    @ApiOperation(value = "根据id逻辑删除医院信息")
    @DeleteMapping("/delete/{id}")
    public Result removeHospSetById(@PathVariable Long id){
        boolean result = hospitalSetService.removeById(id);
        if(result){
            return Result.ok(result);
        }else{
            return Result.fail();
        }
    }

    //条件查询带分页
    @ApiOperation(value = "条件查询带分页")
    @PostMapping("/findPageHospSet/{current}/{limit}")
    public Result findPageHospSet(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo){  //这里采用此注解表示前端传来的数据为json形式，较为方便，false表示可以为空

        Page<HospitalSet> result = hospitalSetService.findPageHospSet(current, limit, hospitalSetQueryVo);
        return Result.ok(result);
    }

    //添加医院设置
    @ApiOperation(value = "添加医院信息")
    @PostMapping("/saveHospitalSet")
    public Result saveHospitalSet(@RequestBody  HospitalSet hospitalSet){
        Integer ResultNum = hospitalSetService.saveHospSet(hospitalSet);
        if(ResultNum != 1){
            return Result.fail();
        }else{
            return Result.ok();
        }
    }
    //根据id获取医院设置
    @ApiOperation(value = "根据id获取医院设置")
    @GetMapping("/getHospSet/{id}")
    public Result getHospSet(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }

    //修改医院设置
    @ApiOperation(value = "修改医院设置")
    @PostMapping("/updateHospitalSet")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet) {
        boolean flag = hospitalSetService.updateById(hospitalSet);
        if(flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //批量删除医院设置
    @ApiOperation(value = "批量删除医院设置")
    @DeleteMapping("/batchRemove")
    public Result batchRemoveHospitalSet(@RequestBody List<Long> idList) {
        hospitalSetService.removeByIds(idList);
        return Result.ok();
    }


    //8 医院设置锁定和解锁
    @ApiOperation(value = "医院设置锁定和解锁")
    @PutMapping("/lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id,
                                  @PathVariable Integer status) {
        //根据id查询医院设置信息
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        //设置状态
        hospitalSet.setStatus(status);
        //调用方法
        hospitalSetService.updateById(hospitalSet);
        return Result.ok();
    }

    //发送签名秘钥
    @ApiOperation(value = "发送签名秘钥")
    @PutMapping("/sendKey/{id}")
    public Result lockHospitalSet(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        //  TODO 发送短信
        return Result.ok();
    }

}
