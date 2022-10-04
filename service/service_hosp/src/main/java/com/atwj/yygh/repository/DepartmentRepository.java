package com.atwj.yygh.repository;

import com.atwj.yygh.model.hosp.Department;
import com.atwj.yygh.model.hosp.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author 吴先森
 * @description: 操纵mongodb数据库
 * @create 2022-10-03 13:17
 */
public interface DepartmentRepository extends MongoRepository<Department,String> {
    Department getDepartmentByHoscodeAndDepcode(String hoscode, String depcode);
}
