package com.tek.repository;

import com.tek.entity.Employee;
import com.tek.exception.ClientException;
import com.tek.repository.interfaces.EmployeeRepoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepo implements EmployeeRepoInterface {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Employee save(Employee employee) {
        if (employee.getId() == null) {
            entityManager.persist(employee);
            return employee;
        } else {
            return entityManager.merge(employee);
        }
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Employee.class, id));
    }

    @Override
    public List<Employee> findAll() {
        return entityManager.createQuery("from Employee", Employee.class).getResultList();
    }

    @Override
    public boolean deleteById(Long id) {
        try {


            Employee employee = findById(id).get();
            entityManager.remove(employee);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Optional<Employee> findByNationalId(String nationalId) {
        try {
            return Optional.ofNullable(entityManager.createQuery("from Employee where nationalId = :nationalId", Employee.class)
                    .setParameter("nationalId", nationalId)
                    .getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
