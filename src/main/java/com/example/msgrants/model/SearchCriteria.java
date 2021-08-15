package com.example.msgrants.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SearchCriteria {

    private boolean student;
    private boolean nuclear;
    private boolean elderly;
    private boolean baby;

}
