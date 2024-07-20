package com.tek;

import com.tek.dto.EmployeeCreationRequest;
import com.tek.exception.ClientException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class EmployeeValidation {
    @Autowired
    private MessageSource messageSource;
    private EmployeeCreationRequest employee;

    public boolean validateEmployee() {
        return checkNationalLength() && checkNameLength() && checkNameChar() && checkAgeFromNationalId();
    }

    private boolean checkNameLength() {
        if (employee.getName().isEmpty() || employee.getName().length() > 100) {
            throw new ClientException("name", messageFromLocalization("name.length"));
        }
        return true;
    }

    private boolean checkNameChar() {
        if (!employee.getName().matches("[A-Z]+")) {
            throw new ClientException("name", messageFromLocalization("name.uppercase"));
        }
        return true;
    }

    private int extractBirthYearFromNationalId() {
        String year=employee.getNationalId().substring(1, 3);
        if (employee.getNationalId().charAt(0)=='2'){
            year="19"+year;
        }
        if (employee.getNationalId().charAt(0)=='3'){
            year="20"+year;
        }
        return Integer.parseInt(year);
    }

    private int calculateAgeFromBirthYear(int birthYear) {
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        return currentYear - birthYear;
    }

    private boolean checkNationalLength() {
        if (employee.getNationalId().length() != 14) {
            throw new ClientException("nationalId", messageFromLocalization("nationalId.length"));
        }
        return true;
    }

    private boolean checkAgeFromNationalId() {
        int year=extractBirthYearFromNationalId();
        // Calculate age from birth year
        int calculatedAge = calculateAgeFromBirthYear(year);
        System.out.println("age is:" +calculatedAge);

        // Check if the calculated age matches the age provided
        if (employee.getAge() != calculatedAge) {
            throw new ClientException("age", messageFromLocalization("age.invalid"));
        }
        return true;
    }


    private String messageFromLocalization(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }
}
