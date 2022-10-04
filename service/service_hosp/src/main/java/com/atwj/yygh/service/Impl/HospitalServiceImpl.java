package com.atwj.yygh.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.atwj.yygh.model.hosp.Hospital;
import com.atwj.yygh.repository.HospitalRepository;
import com.atwj.yygh.service.HospitalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @author 吴先森
 * @description:
 * @create 2022-10-03 13:18
 */
@Service
public class HospitalServiceImpl implements HospitalService {
    @Resource
    private HospitalRepository hospitalRepository;

    @Override
    public void save(Map<String, Object> paramMap) {
        //将map集合转换成hospital对象
        String mapString = JSONObject.toJSONString(paramMap); //将map转换成JSON字符串
        Hospital hospital = JSONObject.parseObject(mapString, Hospital.class); //将JSON字符串转换成hospital对象

        //判断数据库中是否存在此医院信息
        Hospital hospitalExist = hospitalRepository.getHospitalByHoscode(hospital.getHoscode());

        //如果存在进行修改，不存在进行保存
        if(hospitalExist != null){
            hospital.setStatus(hospitalExist.getStatus());
            hospital.setCreateTime(hospitalExist.getCreateTime());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);

            //存储到mongodb数据库中
            hospitalRepository.save(hospital);
        } else{
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);

            hospitalRepository.save(hospital);
        }
    }

    @Override
    public Hospital getByHoscode(String hoscode) {
        Hospital resultHospital = hospitalRepository.getHospitalByHoscode(hoscode);
        return resultHospital;
    }
}
