package com.xdu.yygh.hosp.repository;

import com.xdu.yygh.model.hosp.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends MongoRepository<Department,String> {
    //根据医院编号 和 科室编号查询科室
    Department getDepartmentByHoscodeAndDepcode(String hoscode, String depcode);
}
