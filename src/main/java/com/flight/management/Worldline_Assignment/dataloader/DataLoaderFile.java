package com.flight.management.Worldline_Assignment.dataloader;

import com.flight.management.Worldline_Assignment.model.FlightModel;
import com.flight.management.Worldline_Assignment.repository.FlightRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoaderFile implements CommandLineRunner {

    private final FlightRepository flightRepository;

    public DataLoaderFile(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        flightRepository.save(new FlightModel(null, "Airline1", "Supplier1", 200.0, "DEL", "HYD",
                LocalDateTime.parse("2025-05-01T10:00:00"), LocalDateTime.parse("2025-05-01T14:00:00")));
        flightRepository.save(new FlightModel(null, "Airline2", "Supplier2", 300.0, "HYD", "LHR",
                LocalDateTime.parse("2025-05-02T08:00:00"), LocalDateTime.parse("2025-05-02T12:00:00")));
        flightRepository.save(new FlightModel(null, "Airline3", "Supplier3", 250.0, "LHR", "NYC",
                LocalDateTime.parse("2025-05-03T09:30:00"), LocalDateTime.parse("2025-05-03T12:45:00")));
        flightRepository.save(new FlightModel(null, "Airline4", "Supplier4", 150.0, "NYC", "BOM",
                LocalDateTime.parse("2025-05-04T15:00:00"), LocalDateTime.parse("2025-05-04T17:00:00")));
        flightRepository.save(new FlightModel(null, "Airline5", "Supplier5", 180.0, "BOM", "AMS",
                LocalDateTime.parse("2025-05-05T11:00:00"), LocalDateTime.parse("2025-05-05T13:15:00")));
    }

}

