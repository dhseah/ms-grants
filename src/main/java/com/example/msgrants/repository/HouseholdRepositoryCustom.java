package com.example.msgrants.repository;

import com.example.msgrants.model.Household;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import java.util.List;

public interface HouseholdRepositoryCustom {
    List<Household> findAllMatching(int householdIncome);
}
