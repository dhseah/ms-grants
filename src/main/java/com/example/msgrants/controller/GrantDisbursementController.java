package com.example.msgrants.controller;

import com.example.msgrants.model.Household;
import com.example.msgrants.model.HouseholdMember;
import com.example.msgrants.model.SearchCriteria;
import com.example.msgrants.service.GrantDisbursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GrantDisbursementController {

    @Autowired
    GrantDisbursementService service;

    @PostMapping("/household")
    @ResponseBody
    public Household createHousehold(@RequestBody Household toCreate) {
        return service.createHousehold(toCreate);
    }

    @GetMapping("/household/{id}")
    @ResponseBody
    public Household retrievedHousehold(@PathVariable String id) { return service.retrieveHousehold(id); }

    @PutMapping("/household/{id}")
    @ResponseBody
    public Household addHouseholdMember(@PathVariable String id, @RequestBody HouseholdMember toAdd) {
        return service.addHouseholdMember(id, toAdd);
    }

    @GetMapping("/households")
    @ResponseBody
    public List<Household> searchHouseholds(
            @RequestParam(required = false) Integer income,
            @RequestParam(required = false) String student,
            @RequestParam(required = false) String nuclear,
            @RequestParam(required = false) String elderly,
            @RequestParam(required = false) String baby) {

        SearchCriteria searchCriteria = SearchCriteria.builder()
                .encouragement(income != null && income == 150000)
                .gst(income != null && income == 100000)
                .student(student != null && student.equals("true"))
                .nuclear(nuclear != null && nuclear.equals("true"))
                .elderly(elderly != null && elderly.equals("true"))
                .baby(baby != null && baby.equals("true"))
                .build();

        return service.searchHouseholds(searchCriteria);
    }
}
