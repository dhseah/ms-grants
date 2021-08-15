package com.example.msgrants.service;

import com.example.msgrants.model.Household;
import com.example.msgrants.model.HouseholdMember;
import com.example.msgrants.model.SearchCriteria;
import com.example.msgrants.repository.HouseholdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GrantDisbursementService {

    @Autowired
    HouseholdRepository repository;

    public Household createHousehold(Household toPersist) {
        return repository.save(toPersist);
    }

    public Household retrieveHousehold(String id) {
        return repository.findById(id).get();
    }

    public Household addHouseholdMember(String id, HouseholdMember toAdd) {
        return repository.findAndModify(id, toAdd);
    }

    public List<Household> searchHouseholds(SearchCriteria criteria) {
        return repository.findAllMatching(criteria);
    }

}
