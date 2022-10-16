package com.atwj.yygh.service.Impl;

import com.atwj.yygh.cmn.client.DictFeignClient;
import com.atwj.yygh.enums.DictEnum;
import com.atwj.yygh.mapper.PatientMapper;
import com.atwj.yygh.model.user.Patient;
import com.atwj.yygh.service.PatientService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 吴先森
 * @description:
 * @create 2022-10-12 19:54
 */
@Service
public class PatientServiceImpl extends ServiceImpl<PatientMapper, Patient> implements PatientService {
    @Resource
    private DictFeignClient dictFeignClient;

    //获取就诊人列表
    @Override
    public List<Patient> findAllUserId(Long userId) {
        QueryWrapper<Patient> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        List<Patient> patients = baseMapper.selectList(wrapper);
        //使用远程调用，得到编码对应具体内容，查询数据字典表内容
        patients.stream().forEach(item -> {
            //封装param中其他信息
            this.packPatient(item);
        });
        return patients;
    }

    @Override
    public Patient getPatientId(Long id) {
        return this.packPatient(baseMapper.selectById(id));
    }


    //Patient对象里面其他参数封装
    private Patient packPatient(Patient patient) {
        //根据证件类型编码，获取证件类型具体指
        String certificatesTypeString =
                dictFeignClient.getName(DictEnum.CERTIFICATES_TYPE.getDictCode(), patient.getCertificatesType());//联系人证件
        //联系人证件类型
        String contactsCertificatesTypeString =
                dictFeignClient.getName(DictEnum.CERTIFICATES_TYPE.getDictCode(), patient.getContactsCertificatesType());
        //省份
        String provinceString = dictFeignClient.getName(patient.getProvinceCode());
        //市级
        String cityString = dictFeignClient.getName(patient.getCityCode());
        //区县
        String districtString = dictFeignClient.getName(patient.getDistrictCode());

        //将查询到低数据封装在param参数中
        patient.getParam().put("certificatesTypeString", certificatesTypeString);
        patient.getParam().put("contactsCertificatesTypeString", contactsCertificatesTypeString);
        patient.getParam().put("provinceString", provinceString);
        patient.getParam().put("cityString", cityString);
        patient.getParam().put("districtString", districtString);
        patient.getParam().put("fullAddress", provinceString + cityString + districtString + patient.getAddress());
        return patient;
    }
}
