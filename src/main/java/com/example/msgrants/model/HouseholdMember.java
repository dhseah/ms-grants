package com.example.msgrants.model;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Data
public class HouseholdMember implements Serializable {

    private String name;
    private String gender;
    private String maritalStatus;
    private String spouse;
    private String occupationType;
    private int annualIncome;
    private LocalDate dateOfBirth;

}
