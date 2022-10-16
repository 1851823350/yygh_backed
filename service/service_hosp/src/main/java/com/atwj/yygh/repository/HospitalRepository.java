package com.atwj.yygh.repository;

import com.atwj.yygh.model.hosp.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author 吴先森
 * @description: 操纵mongodb数据库
 * @create 2022-10-03 13:17
 */
public interface HospitalRepository extends MongoRepository<Hospital,String> {
    Hospital getHospitalByHoscode(String hoscode);

    //根据医院名称查询医院信息
    List<Hospital> getHospitalByHosnameLike(String hosname);
}
