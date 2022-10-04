package com.atwj.yygh.service.Impl;

import com.atwj.yygh.common.utils.MD5;
import com.atwj.yygh.mapper.HospitalSetMapper;
import com.atwj.yygh.model.hosp.HospitalSet;
import com.atwj.yygh.service.HospitalSetService;
import com.atwj.yygh.vo.hosp.HospitalSetQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;


/**
 * @author 吴先森
 * @description:
 * @create 2022-09-27 9:36
 */
@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet> implements HospitalSetService {
    @Resource
    private HospitalSetMapper hospitalSetMapper;

    @Override
    public Page<HospitalSet> findPageHospSet(long current, long limit, HospitalSetQueryVo hospitalSetQueryVo) {
        Page<HospitalSet> page = new Page<>(current, limit);
        QueryWrapper<HospitalSet> queryWrapper = new QueryWrapper<>();
        if (hospitalSetQueryVo != null) {
            String hoscode = hospitalSetQueryVo.getHoscode();
            String hosname = hospitalSetQueryVo.getHosname();
            if (!StringUtils.isEmpty(hoscode)) {
                queryWrapper.eq("hoscode", hospitalSetQueryVo.getHoscode());
            }
            if (!StringUtils.isEmpty(hosname)) {
                queryWrapper.like("hosname", hospitalSetQueryVo.getHosname());
            }
        }
        //调用方法实现分页查询
        Page<HospitalSet> pageHospSet = hospitalSetMapper.selectPage(page, queryWrapper);

        return pageHospSet;
    }

    @Override
    public Integer saveHospSet(HospitalSet hospitalSet) {
        //设置状态码：1 表示可用；0 表示不可用
        hospitalSet.setStatus(0);

        //设置签名密钥
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis() + "" + random.nextInt(1000)));

        //添加信息
        int resultNum = hospitalSetMapper.insert(hospitalSet);
        return resultNum;
    }

    //根据hoscode查询签名
    @Override
    public String getSignKey(String hoscode) {
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        wrapper.eq("hoscode",hoscode);
        HospitalSet hospitalSet = baseMapper.selectOne(wrapper);
        return hospitalSet.getSignKey();
    }
}
