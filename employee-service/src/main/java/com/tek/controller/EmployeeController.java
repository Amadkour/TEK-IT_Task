package com.tek.controller;

import com.tek.config.GenericResponse;
import com.tek.dto.EmployeeCreationRequest;
import com.tek.entity.Employee;
import com.tek.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<GenericResponse<Employee>> createEmployee(@RequestBody EmployeeCreationRequest employeeCreationRequest) {
        return GenericResponse.success(employeeService.save(employeeCreationRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<Employee>> getEmployee(@PathVariable Long id) {
        return GenericResponse.success(employeeService.findById(id));
    }

    @GetMapping
    public ResponseEntity<GenericResponse<List<Employee>>> getAllEmployees() {
        return GenericResponse.success(employeeService.findAll());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Object>> deleteEmployee(@PathVariable Long id) {

      return   GenericResponse.successWithMessageOnly(employeeService.deleteById(id));
    }
}
