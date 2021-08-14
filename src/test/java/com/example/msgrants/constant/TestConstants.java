package com.example.msgrants.constant;

import com.example.msgrants.model.HouseholdMember;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestConstants {

    public static List<List<HouseholdMember>> studentEncouragementHouseholds() {
        List<HouseholdMember> household = new ArrayList<>();
        household.add(HouseholdMember.builder()
                .name("male student")
                .gender("M")
                .maritalStatus("Single")
                .occupationType("Student")
                .annualIncome(0)
                .dateOfBirth(LocalDate.of(2010, 1, 1))
                .build());
        household.add(HouseholdMember.builder()
                .name("father")
                .gender("M")
                .maritalStatus("Divorced")
                .occupationType("Employed")
                .annualIncome(100000)
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .build());

        List<List<HouseholdMember>> households = new ArrayList<>();
        households.add(household);

        return households;
    }

}
