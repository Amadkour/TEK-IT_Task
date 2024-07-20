package com.tek.service;

import com.tek.validation.EmployeeValidation;
import com.tek.dto.EmployeeCreationRequest;
import com.tek.entity.Branch;
import com.tek.entity.Employee;
import com.tek.exception.ClientException;
import com.tek.repository.BranchRepo;
import com.tek.repository.EmployeeRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private BranchRepo branchRepo;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    EmployeeValidation validation;

    @Transactional
    public Employee save(EmployeeCreationRequest employeeCreationRequest) {
        validation.setEmployee(employeeCreationRequest);
        validation.validateEmployee();
        Employee employee = new Employee();

        if(employeeCreationRequest.getId() == null ) {
            if (employeeRepo.findByNationalId(employeeCreationRequest.getNationalId()).isPresent()) {
                throw new ClientException("employee", messageSource.getMessage("nationalId.unique", null, LocaleContextHolder.getLocale()));
            }
        }else{
            employee.setId(employeeCreationRequest.getId());
        }
        if (employeeCreationRequest.getAge() != null)
            employee.setAge(employeeCreationRequest.getAge());

        if (employeeCreationRequest.getAge() != null)
            employee.setName(employeeCreationRequest.getName());

        if (employeeCreationRequest.getAge() != null)
            employee.setNationalId(employeeCreationRequest.getNationalId());

        if (employeeCreationRequest.getBranchName() != null) {
            Branch branch;
            Optional<Branch> branchOptional = branchRepo.findByName(employeeCreationRequest.getBranchName());
            if (branchOptional.isPresent()) {
                branch = branchOptional.get();
            } else {
                branch = new Branch();
                branch.setName(employeeCreationRequest.getBranchName());
                branch = branchRepo.save(branch);
            }
            employee.setBranch(branch);
        }
        return employeeRepo.save(employee);
    }

    public Employee findById(Long id) {
        return employeeRepo.findById(id).orElseThrow(() -> new ClientException("employee", messageSource.getMessage("employee.notfound", new Object[]{id}, LocaleContextHolder.getLocale())));
    }

    public Employee findByNationalId(String nationalId) {
        return employeeRepo.findByNationalId(nationalId).orElseThrow(() -> new ClientException("employee", messageSource.getMessage("employee.notfound", new Object[]{nationalId}, LocaleContextHolder.getLocale())));
    }

    public List<Employee> findAll() {
        return employeeRepo.findAll();
    }

    @Transactional
    public String deleteById(Long id) {
        if (employeeRepo.deleteById(id))
            return messageSource.getMessage("employee.deleted", new Object[]{id}, LocaleContextHolder.getLocale());
        else
            return messageSource.getMessage("employee.notfound", new Object[]{id}, LocaleContextHolder.getLocale());


    }


}
