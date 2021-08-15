package com.example.msgrants.repository;

import com.example.msgrants.model.Household;
import com.example.msgrants.model.SearchCriteria;

import java.util.List;

public interface HouseholdRepositoryCustom {
    List<Household> findAllMatching(SearchCriteria criteria);
}
