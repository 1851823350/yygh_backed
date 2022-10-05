package com.atwj.yygh.controller;

import com.atwj.yygh.commonResult.Result;
import com.atwj.yygh.model.cmn.Dict;
import com.atwj.yygh.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@CrossOrigin(allowCredentials = "true")
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

    //根据dictcode和value查询
    @GetMapping("getName/{dictCode}/{value}")
    public String getName(@PathVariable String dictCode,
                          @PathVariable String value) {
        String dictName = dictService.getDictName(dictCode,value);
        return dictName;
    }

    //根据value查询
    @GetMapping("getName/{value}")
    public String getName(@PathVariable String value) {
        String dictName = dictService.getDictName("",value);
        return dictName;
    }

    //根据dict_code查询数据子列表
    @ApiOperation(value = "根据dictCode查询子节点数据列表")
    @GetMapping("/findByDictCode/{dictCode}")
    public Result<List<Dict>> findByDictCode(
            @ApiParam(name = "dictCode", value = "节点编码", required = true)
            @PathVariable String dictCode) {
        List<Dict> list = dictService.getDictByDictCode(dictCode);
        return Result.ok(list);
    }
}
