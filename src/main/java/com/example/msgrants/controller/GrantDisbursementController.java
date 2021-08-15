package com.example.msgrants.controller;

import com.example.msgrants.model.Household;
import com.example.msgrants.model.SearchCriteria;
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
    public List<Household> searchHouseholds(@RequestParam(required = false) Integer income,
                                            @RequestParam(required = false) String student,
                                            @RequestParam(required = false) String nuclear,
                                            @RequestParam(required = false) String elderly,
                                            @RequestParam(required = false) String baby) {

        SearchCriteria searchCriteria = SearchCriteria.builder()
                .encouragement(income != null && income == 150000 )
                .gst(income != null && income == 100000 )
                .student(student != null && student.equals("true"))
                .nuclear(nuclear != null && nuclear.equals("true"))
                .elderly(elderly != null && elderly.equals("true"))
                .baby(baby != null && baby.equals("true"))
                .build();

        return service.searchHouseholds(searchCriteria);
    }
}
