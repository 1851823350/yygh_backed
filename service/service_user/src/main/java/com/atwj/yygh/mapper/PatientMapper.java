package com.atwj.yygh.mapper;

import com.atwj.yygh.model.user.Patient;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 吴先森
 * @description:
 * @create 2022-10-12 19:58
 */
@Mapper
public interface PatientMapper extends BaseMapper<Patient> {
}
