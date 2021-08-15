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

    public static Household newHousehold() {
        return Household.builder()
                .housingType("Condominium")
                .householdMembers(new ArrayList<>())
                .build();
    }

    public static HouseholdMember newHouseholdMember() {
        return HouseholdMember.builder()
                .name("new member")
                .gender("F")
                .maritalStatus("Single")
                .occupationType("Unemployed")
                .annualIncome(0)
                .dateOfBirth(LocalDate.of(2021, 1, 1))
                .build();
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
                .name("husband")
                .gender("M")
                .maritalStatus("Married")
                .occupationType("Employed")
                .annualIncome(100000)
                .dateOfBirth(LocalDate.of(1980, 1, 1))
                .build());

        members.add(HouseholdMember.builder()
                .name("elderly man")
                .gender("M")
                .maritalStatus("Married")
                .occupationType("Unemployed")
                .annualIncome(20000)
                .dateOfBirth(LocalDate.of(1950, 1, 1))
                .build());


        return Household.builder()
                .housingType("HDB")
                .householdMembers(members)
                .build();
    }

    public static Household nuclearHouseholdWithBaby() {
        List<HouseholdMember> members = new ArrayList<>();
        members.add(HouseholdMember.builder()
                .name("husband")
                .gender("M")
                .maritalStatus("Married")
                .spouse("wife")
                .occupationType("Employed")
                .annualIncome(100000)
                .dateOfBirth(LocalDate.of(1980, 1, 1))
                .build());

        members.add(HouseholdMember.builder()
                .name("wife")
                .gender("F")
                .maritalStatus("Married")
                .spouse("husband")
                .occupationType("Employed")
                .annualIncome(100000)
                .dateOfBirth(LocalDate.of(1985, 1, 1))
                .build());

        members.add(HouseholdMember.builder()
                .name("teenage son")
                .gender("M")
                .maritalStatus("Single")
                .occupationType("Unemployed")
                .annualIncome(0)
                .dateOfBirth(LocalDate.of(  2000, 1, 1))
                .build());

        members.add(HouseholdMember.builder()
                .name("baby girl")
                .gender("F")
                .maritalStatus("Single")
                .occupationType("Unemployed")
                .annualIncome(0)
                .dateOfBirth(LocalDate.of(2020, 1, 1))
                .build());

        return Household.builder()
                .housingType("HDB")
                .householdMembers(members)
                .build();
    }

    public static Household richHousehold() {
        List<HouseholdMember> members = new ArrayList<>();
        members.add(HouseholdMember.builder()
                .name("rich husband")
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
                .spouse("rich husband")
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
