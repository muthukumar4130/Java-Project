package com.example.demo.controller;

import com.example.demo.constants.URLConstants;
import com.example.demo.entity.State;
import com.example.demo.exception.FinalResponse;
import com.example.demo.exception.Message;
import com.example.demo.service.impl.StateServiceImpl;
import com.example.demo.utils.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(URLConstants.API)
public class StateController {

    @Autowired
    private StateServiceImpl stateServiceImpl;

    @Autowired
    private Message message;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "API to save state details.", description = "Save state details based on request data" ,security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully store state details."),
            @ApiResponse(responseCode = "500", description = "Service temporarily unavailable.")
    })
    @PostMapping(name = URLConstants.SAVE_STATE)
    public ResponseEntity<?> saveState(@RequestBody State state){
        State apiResponse=stateServiceImpl.saveState(state);
        FinalResponse finalResponse = commonUtil.getSuccessResponse(message.getMessage("1002"), apiResponse);
        return ResponseEntity.ok(finalResponse);
    }

    @Operation(summary = "API to get state list.", description = "get state list based on request data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get state details."),
            @ApiResponse(responseCode = "500", description = "Service temporarily unavailable.")
    })
    @GetMapping(name = URLConstants.GET_STATES)
    public ResponseEntity<?> getStates(){
        List<State> states=stateServiceImpl.getStates();
        FinalResponse finalResponse=commonUtil.getSuccessResponse(message.getMessage("1003"),states);
        return ResponseEntity.ok(finalResponse);
    }
}
