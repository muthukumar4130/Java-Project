package com.example.demo.service.impl;

import com.example.demo.constants.APIConstants;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import com.example.demo.utils.CommonUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CommonUtil commonUtil;

    @Override
    public Employee saveEmployee(Employee employee) throws Exception {
        Employee employee1 = null;
        try {
            employee1=employeeRepository.save(employee);
        }catch (Exception ex){
             commonUtil.handleInternalError("2500", APIConstants.SAVE_EMPLOYEE, ex);
        }
        return employee1;
    }
}
