package com.atwj.yygh.service;

import com.atwj.yygh.model.hosp.Department;
import com.atwj.yygh.vo.hosp.DepartmentQueryVo;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * @author 吴先森
 * @description: 科室服务接口
 * @create 2022-10-04 10:27
 */
public interface DepartmentService {
    //添加科室
    void save(Map<String, Object> paramMap);

    //查询科室信息
    Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo);

    //删除科室接口
    void remove(String hoscode, String depcode);

}
