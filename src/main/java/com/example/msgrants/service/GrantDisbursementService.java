package com.example.msgrants.service;

import com.example.msgrants.model.Household;
import com.example.msgrants.model.HouseholdMember;
import com.example.msgrants.repository.HouseholdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class GrantDisbursementService {

    @Autowired
    HouseholdRepository repository;

    public List<Household> searchHouseholds(int income, boolean student) {
        return repository.findAllMatching(income, student);
    }

}
