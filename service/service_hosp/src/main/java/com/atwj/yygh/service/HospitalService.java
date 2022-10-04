package com.atwj.yygh.service;

import com.atwj.yygh.model.hosp.Hospital;
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
}
