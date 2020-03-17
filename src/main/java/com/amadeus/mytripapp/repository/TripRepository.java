package com.amadeus.mytripapp.repository;

import com.amadeus.mytripapp.domain.Trip;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the Trip entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TripRepository extends MongoRepository<Trip, String> {
}
