package com.yrenh.university.repository;

import com.yrenh.university.entity.Department;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long> {

    Optional<Department> findByName(String name);

    @Query(value = "SELECT AVG(lectors.salary) FROM Department department INNER JOIN department.lectors lectors WHERE department.name = ?1", nativeQuery = false)
    Float getAverageSalaryByDepartmentName(String departmentName);
}
