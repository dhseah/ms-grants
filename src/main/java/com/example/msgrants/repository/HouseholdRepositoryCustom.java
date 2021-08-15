package com.example.msgrants.repository;

import com.example.msgrants.model.Household;
import com.example.msgrants.model.HouseholdMember;
import com.example.msgrants.model.SearchCriteria;

import java.util.List;

public interface HouseholdRepositoryCustom {

    Household findAndModify(String id, HouseholdMember toAdd);

    List<Household> findAllMatching(SearchCriteria criteria);
}
