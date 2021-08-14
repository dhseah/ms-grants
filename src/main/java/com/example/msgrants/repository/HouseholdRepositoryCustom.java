package com.example.msgrants.repository;

import com.example.msgrants.model.Household;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import java.util.List;
import java.util.Optional;

public interface HouseholdRepositoryCustom {
    List<Household> findAllMatching(int householdIncome, boolean student);
}
