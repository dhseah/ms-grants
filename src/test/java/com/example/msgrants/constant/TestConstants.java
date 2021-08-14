package com.example.msgrants.constant;

import com.example.msgrants.model.Household;
import com.example.msgrants.model.HouseholdMember;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestConstants {

    public static List<Household> studentEncouragementHouseholds() {

        List<Household> households = new ArrayList<>();
        households.add(householdWithStudent());

        return households;
    }

    public static Household householdWithStudent() {
        List<HouseholdMember> members = new ArrayList<>();
        members.add(HouseholdMember.builder()
                .name("male student")
                .gender("M")
                .maritalStatus("Single")
                .occupationType("Student")
                .annualIncome(0)
                .dateOfBirth(LocalDate.of(2010, 1, 1))
                .build());
        members.add(HouseholdMember.builder()
                .name("father")
                .gender("M")
                .maritalStatus("Divorced")
                .occupationType("Employed")
                .annualIncome(100000)
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .build());

        return Household.builder()
                .housingType("HDB")
                .householdMembers(members)
                .build();
    }

    public static Household householdWithElderly() {
        List<HouseholdMember> members = new ArrayList<>();
        members.add(HouseholdMember.builder()
                .name("elderly man")
                .gender("M")
                .maritalStatus("Married")
                .occupationType("Unemployed")
                .annualIncome(2000)
                .dateOfBirth(LocalDate.of(1950, 1, 1))
                .build());


        return Household.builder()
                .housingType("HDB")
                .householdMembers(members)
                .build();
    }

    public static Household richHousehold() {
        List<HouseholdMember> members = new ArrayList<>();
        members.add(HouseholdMember.builder()
                .name("rich hushband")
                .gender("M")
                .maritalStatus("Married")
                .spouse("rich wife")
                .occupationType("Employed")
                .annualIncome(500000)
                .dateOfBirth(LocalDate.of(1980, 1, 1))
                .build());

        members.add(HouseholdMember.builder()
                .name("rich wife")
                .gender("F")
                .maritalStatus("Married")
                .occupationType("Employed")
                .annualIncome(500000)
                .dateOfBirth(LocalDate.of(1985, 1, 1))
                .build());

        return Household.builder()
                .housingType("Landed")
                .householdMembers(members)
                .build();
    }

}
