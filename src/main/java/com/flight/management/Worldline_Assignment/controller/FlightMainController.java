package com.flight.management.Worldline_Assignment.controller;

import com.flight.management.Worldline_Assignment.dto.FlightSearchDTO;
import com.flight.management.Worldline_Assignment.exception.BadRequestException;
import com.flight.management.Worldline_Assignment.model.FlightModel;
import com.flight.management.Worldline_Assignment.service.FlightService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightMainController {


    private final FlightService flightService;

    @PostMapping("/addflight")
    public ResponseEntity<FlightModel> createFlight(@Valid @RequestBody FlightModel flight) {
        if(flight == null){
         throw new BadRequestException("Flight data is missing in the request");
        }
        return ResponseEntity.ok(flightService.saveFlight(flight));
    }

    @GetMapping
    public ResponseEntity<List<FlightModel>> getAllFlights() {

        return ResponseEntity.ok(flightService.getAllFlights());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightModel> updateFlight(@PathVariable Long id, @Valid @RequestBody FlightModel flight) {
        FlightModel updatedFlight = flightService.updateFlight(id, flight);
        return ResponseEntity.ok(updatedFlight);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightModel> getFlight(@PathVariable Long id) {
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseEntity<List<FlightModel>> searchFlights(@Valid @RequestBody FlightSearchDTO request) {
        return ResponseEntity.ok(flightService.searchFlights(request));

    }
}
