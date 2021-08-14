package com.example.msgrants.controller;

import com.example.msgrants.model.Household;
import com.example.msgrants.service.GrantDisbursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GrantDisbursementController {

    @Autowired
    GrantDisbursementService service;

    @GetMapping("/household")
    @ResponseBody
    public List<Household> searchHouseholds(@RequestParam(name = "income") int income) {
        return service.searchHouseholds(income);
    }
}
