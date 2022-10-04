package com.atwj.yygh.service;

import com.atwj.yygh.model.hosp.HospitalSet;
import com.atwj.yygh.vo.hosp.HospitalSetQueryVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

/**
 * @author 吴先森
 * @description: 预约系统后台医院信息设置接口
 * @create 2022-09-27 9:34
 */
@Service
public interface HospitalSetService extends IService<HospitalSet> {

    //条件查询带分页
    public Page<HospitalSet> findPageHospSet(long current, long limit, HospitalSetQueryVo hospitalSetQueryVo);

    //添加医院信息
    public Integer saveHospSet(HospitalSet hospitalSet);

    //获取密钥
    String getSignKey(String hoscode);
}
