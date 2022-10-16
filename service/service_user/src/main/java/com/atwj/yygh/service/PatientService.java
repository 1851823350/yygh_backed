package com.atwj.yygh.service;

import com.atwj.yygh.model.user.Patient;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author 吴先森
 * @description:
 * @create 2022-10-12 19:54
 */
public interface PatientService extends IService<Patient> {

    //查找就诊人列表
    List<Patient> findAllUserId(Long userId);

    //根据id获取就诊人信息
    Patient getPatientId(Long id);
}
