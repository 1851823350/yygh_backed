package com.atwj.yygh.service;

import com.atwj.yygh.model.hosp.Hospital;
import com.atwj.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 吴先森
 * @description:  外部医院系统设置接口
 * @create 2022-10-03 13:20
 */
@Service
public interface HospitalService {
    //添加医院信息
    void save(Map<String, Object> paramMap);

    //根据hoscode查询医院信息
    Hospital getByHoscode(String hoscode);

    //分页查询医院信息
    Page<Hospital> selectPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo);

    //更改医院上线状态
    void updateStatus(String id, Integer status);
}
