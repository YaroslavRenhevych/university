package com.yrenh.university.repository;

import com.yrenh.university.entity.Degree;
import com.yrenh.university.entity.Department;
import com.yrenh.university.entity.Lector;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectorRepository extends CrudRepository<Lector, Long> {

    List<Lector> findByDepartmentsAndDegree(Department department, Degree degree);

    List<Lector> findByFirstNameContainingOrLastNameContaining(String firstnameSearchStr, String lastnameSearchStr);
}
