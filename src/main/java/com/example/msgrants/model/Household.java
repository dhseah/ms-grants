package com.example.msgrants.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Document
@Builder
@Data
public class Household implements Serializable {

    @Id
    private String id;
    private String housingType;
    private List<HouseholdMember> householdMembers;

}
