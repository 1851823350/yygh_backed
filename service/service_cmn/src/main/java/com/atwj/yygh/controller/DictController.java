package com.atwj.yygh.controller;

import com.atwj.yygh.commonResult.Result;
import com.atwj.yygh.model.cmn.Dict;
import com.atwj.yygh.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 吴先森
 * @description:
 * @create 2022-10-01 8:54
 */
@Api(tags = "数据字典接口")
@RestController
@RequestMapping("/admin/cmn/dict")
@CrossOrigin
public class DictController {
    @Resource
    private DictService dictService;


    @ApiOperation(value = "根据id查询子数据列表")
    @GetMapping("/findChildrenData/{id}")
    public Result findChildrenData(@PathVariable Long id){
        List<Dict> ResultList = dictService.findChildrenData(id);
        return Result.ok(ResultList);
    }

    @ApiOperation(value = "导出数据字典接口")
    @GetMapping("/exportData")
    public void exportDict(HttpServletResponse response) {
        dictService.exportDictData(response);
    }

    @ApiOperation(value = "导入数据字典接口")
    @PostMapping("/importData")
    public Result importData(MultipartFile file) {
        dictService.importData(file);
        return Result.ok();
    }
}
