package com.example.msgrants.repository;

import com.example.msgrants.model.Household;

import java.util.List;

public interface HouseholdRepositoryCustom {
    List<Household> findAllMatching(int householdIncome, boolean student);
}
