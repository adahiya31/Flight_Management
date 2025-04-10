package com.flightmanagement;

import com.flight.management.Worldline_Assignment.exception.ResourceNotFoundException;
import com.flight.management.Worldline_Assignment.model.FlightModel;
import com.flight.management.Worldline_Assignment.repository.FlightRepository;
import com.flight.management.Worldline_Assignment.service.CrazySupplierClients;
import com.flight.management.Worldline_Assignment.service.FlightService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = FlightManagementApplicationTests.class)
public class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightService flightService;

    @Mock
    private CrazySupplierClients crazySupplierClient;

    @Test
    public void testSaveFlight() {
        FlightModel flight = new FlightModel();
        flight.setAirline("Test Airline");
        flight.setFare(500.0);


        when(flightRepository.save(flight)).thenReturn(flight);

        FlightModel savedFlight = flightService.saveFlight(flight);

        assertNotNull(savedFlight);
        assertEquals(flight.getAirline(), savedFlight.getAirline());
    }

    @Test
    public void testGetFlightById() {
        FlightModel flight = new FlightModel();
        flight.setId(1L);
        flight.setAirline("Test Airline");
        flight.setFare(500.0);

        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));

        FlightModel foundFlight = flightService.getFlightById(1L);

        assertNotNull(foundFlight);
        assertEquals(flight.getId(), foundFlight.getId());
        assertEquals(flight.getAirline(), foundFlight.getAirline());
    }

    @Test
    public void testGetFlightByIdNotFound() {
        when(flightRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> flightService.getFlightById(1L));
    }
    @Test
    public void testDeleteFlight() {
        FlightModel flight = new FlightModel();
        flight.setId(1L);
        when(flightRepository.existsById(1L)).thenReturn(true);

        flightService.deleteFlight(1L);

        verify(flightRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteFlightNotFound() {
        when(flightRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> flightService.deleteFlight(1L));
    }


    @Test
    public void testSearchFlights() {
        FlightSearch request = new FlightSearch();
        request.setOrigin("DEL");
        request.setDestination("LAX");
        request.setOutboundDate(LocalDateTime.now());

        List<Flight> dbFlights = List.of(
                new Flight(1L, "Airline1", "Supplier1", 200.0, "DEL", "LAX", LocalDateTime.now(), LocalDateTime.now().plusHours(3))
        );

        when(flightRepository.findAll()).thenReturn(dbFlights);

        when(crazySupplierClient.getFlights(any()))
                .thenReturn(List.of(
                        new CrazySupplierResponse("Airline2", 250.0, 30.0, "DEL", "LAX", "2025-05-01", "2025-05-01")
                ));

         List<Flight> result = flightService.searchFlights(request);

        assertNotNull(result);
        assertEquals(2, result.size());
    }


}

