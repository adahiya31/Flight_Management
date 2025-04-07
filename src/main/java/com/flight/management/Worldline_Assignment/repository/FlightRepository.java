package com.flight.management.Worldline_Assignment.repository;

import com.flight.management.Worldline_Assignment.model.FlightModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<FlightModel, Long> {
}
