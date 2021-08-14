package com.example.msgrants.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseholdMember implements Serializable {

    private String name;
    private String gender;
    private String maritalStatus;
    private String spouse;
    private String occupationType;
    private int annualIncome;
    private LocalDate dateOfBirth;

}
