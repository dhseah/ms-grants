package com.example.msgrants.repository;

import com.example.msgrants.model.Household;
import com.example.msgrants.model.HouseholdMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public class HouseholdRepositoryCustomImpl implements HouseholdRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public HouseholdRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Household> findAllMatching(int incomeLimit, boolean student) {
        Aggregation aggregation = newAggregation(calHouseholdIncome, filterByIncome(incomeLimit));
        AggregationResults<Household> results = mongoTemplate.aggregate(aggregation, "household", Household.class);
        return results.getMappedResults();
    }

    AggregationExpression householdIncome = AccumulatorOperators.Sum.sumOf("householdMembers.annualIncome");
    ProjectionOperation calHouseholdIncome = project("housingType", "householdMembers").and(householdIncome).as("householdIncome");
    MatchOperation filterByIncome(int income) {
        return match(new Criteria("householdIncome").lt(income));
    }


}
