package com.atwj.yygh.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.atwj.yygh.model.hosp.Department;
import com.atwj.yygh.repository.DepartmentRepository;
import com.atwj.yygh.service.DepartmentService;
import com.atwj.yygh.vo.hosp.DepartmentQueryVo;
import com.atwj.yygh.vo.hosp.DepartmentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 吴先森
 * @description:
 * @create 2022-10-04 10:28
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    private DepartmentRepository departmentRepository;

    @Override
    public void save(Map<String, Object> paramMap) {
        String mapString = JSONObject.toJSONString(paramMap);
        Department department = JSONObject.parseObject(mapString, Department.class);

        //判断数据库中是否存在此医院信息
        Department resultDepartment = departmentRepository.getDepartmentByHoscodeAndDepcode(department.getHoscode(),
                department.getDepcode());
        //判断department是否存在，不存在则为添加，存在则为修改
        if (resultDepartment != null) {
            resultDepartment.setUpdateTime(new Date());
            resultDepartment.setIsDeleted(0);
            departmentRepository.save(resultDepartment);
        } else {
            department.setCreateTime(new Date());
            department.setUpdateTime(new Date());
            department.setIsDeleted(0);
            departmentRepository.save(department);
        }
    }

    @Override
    public Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo) {
        // 创建Pageable对象，设置当前页和每页记录数
        //0是第一页
        Pageable pageable = PageRequest.of(page - 1, limit);
        // 创建Example对象
        Department department = new Department();
        BeanUtils.copyProperties(departmentQueryVo, department);
        department.setIsDeleted(0);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
        Example<Department> example = Example.of(department, matcher);

        Page<Department> all = departmentRepository.findAll(example, pageable);
        return all;
    }

    @Override
    public void remove(String hoscode, String depcode) {
        Department resultDepartment = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if (resultDepartment != null) {
            departmentRepository.deleteById(resultDepartment.getId());
        }
    }

    @Override
    public List<DepartmentVo> findDeptTree(String hoscode) {
        ArrayList<DepartmentVo> result = new ArrayList<>();

        //根据编号查询所有科室信息
        Department departmentQuery = new Department();
        departmentQuery.setHoscode(hoscode);
        Example<Department> example = Example.of(departmentQuery);
        List<Department> departmentList = departmentRepository.findAll(example);

        //根据大科室编号进行分组，利用java8之stream流，获取大科室下的小科室信息
        Map<String, List<Department>> departmentMap = departmentList.stream().
                collect(Collectors.groupingBy(Department::getBigcode));

        //遍历map集合
        for (Map.Entry<String, List<Department>> entry : departmentMap.entrySet()) {
            //获取大科室编号
            String bigCode = entry.getKey();
            //获取大科室编号对应的全部数据
            List<Department> departmentValues = entry.getValue();
            //封装大科室信息
            DepartmentVo departmentVo1 = new DepartmentVo();
            departmentVo1.setDepcode(bigCode);
            departmentVo1.setDepname(departmentValues.get(0).getBigname());
            //封装小科室
            List<DepartmentVo> childrenDepartments = new ArrayList<>();
            for (Department department : departmentValues) {
                DepartmentVo departmentVo2 = new DepartmentVo();
                departmentVo2.setDepcode(department.getDepcode());
                departmentVo2.setDepname(department.getDepname());
                //封装到list集合
                childrenDepartments.add(departmentVo2);
            }
            //将小科室放入大科室里面
            departmentVo1.setChildren(childrenDepartments);
            //再将大科室信息放入返回结果中
            result.add(departmentVo1);
        }
        return result;
    }

    @Override
    public String getDepName(String hoscode, String depcode) {
        Department resultDepartment = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        String depname = resultDepartment.getDepname();
        return depname;
    }

    @Override
    public Department getDepartment(String hoscode, String depcode) {
        return departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
    }
}
