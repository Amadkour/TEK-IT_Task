package com.tek.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeCreationRequest {

    private String name;
    private Integer age;
    private String nationalId;
    private String branchName;

}
