package com.flight.management.Worldline_Assignment.service;


import com.flight.management.Worldline_Assignment.dto.CrazySupplierRequestDTO;
import com.flight.management.Worldline_Assignment.dto.CrazySupplierResponseDTO;
import com.flight.management.Worldline_Assignment.dto.FlightSearchDTO;
import com.flight.management.Worldline_Assignment.exception.ExternalServiceException;
import com.flight.management.Worldline_Assignment.exception.ResourceNotFoundException;
import com.flight.management.Worldline_Assignment.model.FlightModel;
import com.flight.management.Worldline_Assignment.repository.FlightRepository;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FlightService {

    private final FlightRepository flightRepository;
    private final CrazySupplierClients crazySupplierClient;


    public FlightModel saveFlight(FlightModel flight) {
        return flightRepository.save(flight);
    }
    public List<FlightModel> getAllFlights() {
        return flightRepository.findAll();
    }

    public FlightModel updateFlight(Long id, FlightModel flight) {
        FlightModel existingFlight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with ID: " + id));

        existingFlight.setAirline(flight.getAirline());
        existingFlight.setSupplier(flight.getSupplier());
        existingFlight.setFare(flight.getFare());
        existingFlight.setDepartureAirport(flight.getDepartureAirport());
        existingFlight.setDestinationAirport(flight.getDestinationAirport());
        existingFlight.setDepartureTime(flight.getDepartureTime());
        existingFlight.setArrivalTime(flight.getArrivalTime());

         return flightRepository.save(existingFlight);
    }

    public FlightModel getFlightById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with ID: " + id));
    }

    public void deleteFlight(Long id) {
        if (!flightRepository.existsById(id)) {
            throw new ResourceNotFoundException("Flight not found with ID: " + id);
        }
        flightRepository.deleteById(id);
    }

    public List<FlightModel> searchFlights(FlightSearchDTO request) {
        List<FlightModel> dbFlights = flightRepository.findAll();

        CrazySupplierRequestDTO supplierRequest = new CrazySupplierRequestDTO();
        supplierRequest.setFrom(request.getOrigin());
        supplierRequest.setTo(request.getDestination());
        supplierRequest.setOutboundDate(request.getOutboundDate() != null ? request.getOutboundDate() : null);
        supplierRequest.setInboundDate(request.getInboundDate() != null ? request.getInboundDate() : null);


        List<CrazySupplierResponseDTO> supplierFlights;
        try {
            supplierFlights = crazySupplierClient.getFlights(supplierRequest);
        } catch (FeignException e) {
            throw new ExternalServiceException("Failed to fetch flights from CrazySupplierAPI");
        }

        List<FlightModel> flightResult = new ArrayList<>(dbFlights);

        for (CrazySupplierResponseDTO res : supplierFlights) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

            LocalDateTime departure = res.getOutboundDateTime();
            LocalDateTime arrival = res.getInboundDateTime();

            FlightModel flight = FlightModel.builder()
                    .airline(res.getCarrier())
                    .supplier("CrazySupplier")
                    .fare(res.getBasePrice() + res.getTax())
                    .departureAirport(res.getDepartureAirportName())
                    .destinationAirport(res.getArrivalAirportName())
                    .departureTime(departure)
                    .arrivalTime(arrival)
                    .build();

            flightResult.add(flight);
        }

        return flightResult;
    }

    @CircuitBreaker(name = "crazySupplierService", fallbackMethod = "fallbackFlights")
    public List<CrazySupplierResponseDTO> getCrazySupplierFlights(CrazySupplierRequestDTO request) {
        return crazySupplierClient.getFlights(request);
    }

    public List<CrazySupplierResponseDTO> fallbackFlights(CrazySupplierRequestDTO request, Throwable throwable) {
        System.out.println("Fallback triggered for CrazySupplier: " + throwable.getMessage());
        return new ArrayList<>();
    }
}