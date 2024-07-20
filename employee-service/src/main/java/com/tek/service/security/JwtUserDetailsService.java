package com.tek.service.security;

import com.tek.entity.Employee;
import com.tek.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    EmployeeService employeeService;

    @Autowired
    JwtService jwtService;

    public Employee findEmployeeByNationalId(String nationalId) {
        return employeeService.findByNationalId(nationalId);
    }

    @Override
    public UserDetails loadUserByUsername(String nationalId) {
        Employee employee = findEmployeeByNationalId(nationalId);
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(employee.getAge().toString()));
        return new User(
                String.valueOf(employee.getNationalId()), employee.getNationalId(),
                authorityList);
    }

    public String createToken(String nationalId) {
        UserDetails userDetails = loadUserByUsername(nationalId);
        return jwtService.generateToken(userDetails.getUsername());
    }
}