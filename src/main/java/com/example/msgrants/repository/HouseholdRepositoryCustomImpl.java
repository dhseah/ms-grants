package com.example.msgrants.repository;

import com.example.msgrants.model.Household;
import com.example.msgrants.model.SearchCriteria;
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
    public List<Household> findAllMatching(SearchCriteria criteria) {

        AggregationExpression filtersToInclude = filtersToInclude(criteria);

        ProjectionOperation projectHouseholdIncome = projectHouseholdIncome();
        ProjectionOperation projectQualifyingMembers = projectQualifyingMembers(filtersToInclude);

        // calculate household income for all households
        // then filter household based on income and other criteria
        // then list households with household members who qualify for grant(s)
        Aggregation aggregation = newAggregation(projectHouseholdIncome, matchHouseholds(criteria), projectQualifyingMembers);
        AggregationResults<Household> results = mongoTemplate.aggregate(aggregation, "household", Household.class);
        return results.getMappedResults();

    }

    // projectHouseholdIncome ask mongoDB to calculate household income
    // for every household in the collection
    ProjectionOperation projectHouseholdIncome() {
        return project("housingType", "householdMembers")
                .and(AccumulatorOperators.Sum.sumOf("householdMembers.annualIncome")).as("householdIncome");
    }

    // projectQualifyingMembers ask mongoDB to replace the list of
    // all household members to a list of members who qualify for grant(s)
    ProjectionOperation projectQualifyingMembers(AggregationExpression filtersToInclude) {
        return project("housingType")
                .and(ArrayOperators.Filter
                        .filter("householdMembers")
                        .as("qualifyingMembers")
                        .by(filtersToInclude))
                .as("householdMembers");
    }

    // filtersToInclude is a helper function to generate an aggregation
    // expression for filtering household members who qualify for grant(s)
    AggregationExpression filtersToInclude(SearchCriteria criteria) {

        List<AggregationExpression> expressionList = new ArrayList<>();

        if (criteria.isStudent()) expressionList.add(filterStudents);
        if (criteria.isNuclear()) expressionList.add(filterNuclear);
        if (criteria.isElderly()) expressionList.add(filterElderly);
        if (criteria.isBaby()) expressionList.add(filterBaby);

        // if no criterion was provided, add an expression that always
        // evaluate to true in order to get every household members
        if (expressionList.size() == 0) expressionList.add(filterNone);

        AggregationExpression[] expressionArr = new AggregationExpression[expressionList.size()];
        expressionArr = expressionList.toArray(expressionArr);

        return BooleanOperators.Or.or(expressionArr);
    }

    // Expressions for projection stage of aggregation
    AggregationExpression filterNone = ComparisonOperators.Eq.valueOf("qualifyingMembers.name").equalTo("qualifyingMembers.name");
    AggregationExpression filterStudents = ComparisonOperators.Eq.valueOf("qualifyingMembers.occupationType").equalToValue("Student");
    // TODO: update these placeholders
    AggregationExpression filterNuclear = ComparisonOperators.Eq.valueOf("qualifyingMembers.name").equalTo("qualifyingMembers.name");
    AggregationExpression filterElderly = ComparisonOperators.Eq.valueOf("qualifyingMembers.name").equalTo("qualifyingMembers.name");
    AggregationExpression filterBaby = ComparisonOperators.Eq.valueOf("qualifyingMembers.name").equalTo("qualifyingMembers.name");

    // matchHouseholds asks mongoDB to filter household according to a list
    // of criteria that is generated based on the received request parameters
    MatchOperation matchHouseholds(SearchCriteria criteria) {

        List<Criteria> list = new ArrayList<>();
        list.add(noFilter);

        if (criteria.isEncouragement()) list.add(filterHouseholdIncome150000);
        if (criteria.isGst()) list.add(filterHouseholdIncome100000);
        if (criteria.isStudent()) list.add(filterHouseholdWithStudent);
        if (criteria.isNuclear()) list.add(filterHouseholdNuclear);
        if (criteria.isElderly()) list.add(filterHouseholdWithElderly);
        if (criteria.isBaby()) list.add(filterHouseholdWithBaby);

        Criteria criterion = new Criteria();
        criterion.andOperator(list);

        return match(criterion);
    }

    // Criteria for matching stage of aggregation
    Criteria noFilter = new Criteria("householdIncome").gte(0);
    Criteria filterHouseholdIncome150000 = new Criteria("householdIncome").lt(150000);
    Criteria filterHouseholdIncome100000 = new Criteria("householdIncome").lt(100000);
    Criteria filterHouseholdWithStudent = new Criteria("householdMembers.occupationType").is("Student");
    // TODO: update these placeholders
    Criteria filterHouseholdNuclear = new Criteria("householdMembers.occupationType").is("Student");
    Criteria filterHouseholdWithElderly = new Criteria("householdMembers.occupationType").is("Student");
    Criteria filterHouseholdWithBaby = new Criteria("householdMembers.occupationType").is("Student");

}
