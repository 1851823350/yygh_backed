package com.atwj.yygh.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.atwj.yygh.listener.DictListener;
import com.atwj.yygh.mapper.DictMapper;
import com.atwj.yygh.model.cmn.Dict;
import com.atwj.yygh.service.DictService;
import com.atwj.yygh.vo.cmn.DictEeVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 吴先森
 * @description:
 * @create 2022-10-01 8:48
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Resource
    private DictMapper dictMapper;


    //根据数据id查询子数据列表
    @Cacheable(value = "dict",keyGenerator = "keyGenerator")  //按照配置文件中自定义方法生成key，其形式为：dict + keyGenerator方法生成的字段
    @Override
    public List<Dict> findChildrenData(Long id) {
        QueryWrapper<Dict> DictWrapper = new QueryWrapper<>();
        DictWrapper.eq("parent_id", id);
        List<Dict> dictList = dictMapper.selectList(DictWrapper);

        //判断所查询的数据下面是否含有子节点
        for (Dict dict : dictList) {
            Long dictId = dict.getId();
            boolean hasChildren = isChildren(dictId);
            dict.setHasChildren(hasChildren);
        }
        return dictList;
    }

    @Override
    public void exportDictData(HttpServletResponse response) {
        //设置下载信息
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = "dict";
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        //查询数据库
        List<Dict> selectList = dictMapper.selectList(null);

        //将查询到的数据转换成DictEeVo对象
        ArrayList<DictEeVo> dictEeVoList = new ArrayList<>();
        for (Dict dict : selectList) {
            DictEeVo dictEeVo = new DictEeVo();
            BeanUtils.copyProperties(dict, dictEeVo);
            dictEeVoList.add(dictEeVo);
        }

        //将数据写入表中
        try {
            EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("dict")
                    .doWrite(dictEeVoList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @CacheEvict(value = "dict", allEntries=true)  //这里表示当调用此方法，则清空dict块下的所有缓存
    @Override
    public void importData(MultipartFile file) {
        try {
            EasyExcelFactory.read(file.getInputStream(),
                                        DictEeVo.class,
                                  new DictListener(dictMapper)).sheet().doRead();
                } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //判断id是否含有子节点
    public boolean isChildren(Long id) {
        QueryWrapper<Dict> DictWrapper = new QueryWrapper<>();
        DictWrapper.eq("parent_id", id);
        Integer count = dictMapper.selectCount(DictWrapper);
        return count > 0;
    }
}
