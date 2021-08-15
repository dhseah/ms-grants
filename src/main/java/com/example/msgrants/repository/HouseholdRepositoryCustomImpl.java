package com.example.msgrants.repository;

import com.example.msgrants.model.Household;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public class HouseholdRepositoryCustomImpl implements HouseholdRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public HouseholdRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Household> findAllMatching(int incomeLimit, boolean student) {

        AggregationExpression filterStudents = ComparisonOperators.Eq.valueOf("qualifyingMembers.occupationType").equalToValue("Student");
        AggregationExpression filterEmployed = ComparisonOperators.Eq.valueOf("qualifyingMembers.occupationType").equalToValue("Employed");

        List<AggregationExpression> expressionList = new ArrayList<>();
        if (student) expressionList.add(filterStudents);

        AggregationExpression[] expressionArr = new AggregationExpression[expressionList.size()];
        expressionArr = expressionList.toArray(expressionArr);

        AggregationExpression filtersToInclude;
        filtersToInclude = BooleanOperators.Or.or(expressionArr);

        ProjectionOperation qualifyingMembers;
        if (expressionArr.length == 0) {
            qualifyingMembers = project("housingType")
                    .and(ArrayOperators.Filter
                            .filter("householdMembers")
                            .as("qualifyingMembers")
                            .by(""))
                    .as("householdMembers");

        } else {
            qualifyingMembers = project("housingType")
                    .and(ArrayOperators.Filter
                            .filter("householdMembers")
                            .as("qualifyingMembers")
                            .by(filtersToInclude))
                    .as("householdMembers");

        }

        Aggregation aggregation = newAggregation(calHouseholdIncome, filerHouseholds(incomeLimit, student), qualifyingMembers);
        AggregationResults<Household> results = mongoTemplate.aggregate(aggregation, "household", Household.class);
        return results.getMappedResults();
    }

    ProjectionOperation calHouseholdIncome = project("housingType", "householdMembers")
            .and(AccumulatorOperators.Sum.sumOf("householdMembers.annualIncome")).as("householdIncome");


    MatchOperation filerHouseholds(int incomeLimit, boolean student) {
        List<Criteria> criterias = new ArrayList<>();
        criterias.add(filterByIncome(incomeLimit));
        if (student) criterias.add(filterHouseholdWithStudent);

        Criteria criteria = new Criteria();
        criteria.andOperator(criterias);

        return match(criteria);
    }

    Criteria filterByIncome(int income) {
        return new Criteria("householdIncome").lt(income);
    }

    Criteria filterHouseholdWithStudent = new Criteria("householdMembers.occupationType").is("Student");

}
