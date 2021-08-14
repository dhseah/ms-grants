package com.example.msgrants.repository;

import com.example.msgrants.model.Household;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HouseholdRepository extends MongoRepository<Household, String>, HouseholdRepositoryCustom {
}
