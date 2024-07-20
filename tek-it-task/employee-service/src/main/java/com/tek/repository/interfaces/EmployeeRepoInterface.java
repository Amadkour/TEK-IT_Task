package com.tek.repository.interfaces;

import com.tek.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepoInterface {
    Employee  save(Employee employee);
    Optional<Employee> findById(Long id);
    List<Employee> findAll();
    boolean deleteById(Long id);
    Optional<Employee> findByNationalId(String nationalId);
}