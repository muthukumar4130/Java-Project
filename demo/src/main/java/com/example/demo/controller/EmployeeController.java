package com.example.demo.controller;

import com.example.demo.constants.URLConstants;
import com.example.demo.entity.Employee;
import com.example.demo.exception.FinalResponse;
import com.example.demo.exception.Message;
import com.example.demo.service.impl.EmployeeServiceImpl;
import com.example.demo.utils.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(URLConstants.API)
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeServiceImpl;

    @Autowired
    private Message message;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "API to save employee details.", description = "Save employee details based on request data" ,security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully store employee details."),
            @ApiResponse(responseCode = "500", description = "Service temporarily unavailable.")
    })
    @PostMapping(path = URLConstants.SAVE_EMPLOYEE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveEmployee(@RequestBody Employee employee) throws Exception {
        Employee apiResponse= employeeServiceImpl.saveEmployee(employee);
        FinalResponse finalResponse = commonUtil.getSuccessResponse(message.getMessage("1001"), apiResponse);
        return ResponseEntity.ok(finalResponse);
    }
}
