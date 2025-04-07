package com.flightmanagement;

import com.flight.management.Worldline_Assignment.controller.FlightMainController;
import com.flight.management.Worldline_Assignment.model.FlightModel;
import com.flight.management.Worldline_Assignment.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FlightControllerTest {

    @Mock
    private FlightService flightService;

    @InjectMocks
    private FlightMainController flightController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(flightController).build();
    }

    @Test
    void testCreateFlight() throws Exception {
        FlightModel flight = FlightModel.builder()
                .airline("Test Airline")
                .supplier("Test Supplier")
                .fare(300.00)
                .departureAirport("HYD")
                .destinationAirport("DEL")
                .departureTime(LocalDateTime.now().plusHours(1))
                .arrivalTime(LocalDateTime.now().plusHours(2))
                .build();

        when(flightService.saveFlight(any(FlightModel.class))).thenReturn(flight);

        mockMvc.perform(post("/api/flights/addflight")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"airline\":\"Test Airline\",\"supplier\":\"Test Supplier\",\"fare\":300.00,\"departureAirport\":\"HYD\",\"destinationAirport\":\"DEL\",\"departureTime\":\"2025-04-07T12:00:00\",\"arrivalTime\":\"2025-04-07T14:00:00\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.airline").value("Test Airline"))
                .andExpect(jsonPath("$.fare").value(300.00));

        verify(flightService, times(1)).saveFlight(any(FlightModel.class));
    }

    @Test
    void testDeleteFlight() throws Exception {
        Long validId = 1L; 

        mockMvc.perform(delete("/api/flights/{id}", validId))
                .andExpect(status().isNoContent());

        verify(flightService, times(1)).deleteFlight(validId);
    }

    @Test
    void testUpdateFlightValid() throws Exception {
        Long flightId = 1L;
        FlightModel updatedFlight = FlightModel.builder()
                .airline("Updated Airline")
                .supplier("Updated Supplier")
                .fare(350.00)
                .departureAirport("HYD")
                .destinationAirport("AMS")
                .departureTime(LocalDateTime.now().plusHours(1))
                .arrivalTime(LocalDateTime.now().plusHours(2))
                .build();

        when(flightService.updateFlight(eq(flightId), any(FlightModel.class))).thenReturn(updatedFlight);

        mockMvc.perform(put("/api/flights/{id}", flightId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"airline\":\"Updated Airline\",\"supplier\":\"Updated Supplier\",\"fare\":350.00,\"departureAirport\":\"HYD\",\"destinationAirport\":\"AMS\",\"departureTime\":\"2025-04-07T12:00:00\",\"arrivalTime\":\"2025-04-07T14:00:00\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.airline").value("Updated Airline"))
                .andExpect(jsonPath("$.fare").value(350.00));

        verify(flightService, times(1)).updateFlight(eq(flightId), any(FlightModel.class));
    }

    @Test
    void testGetAllFlights() throws Exception {
        FlightModel flight1 = FlightModel.builder()
                .airline("Test Airline 1")
                .supplier("Test Supplier 1")
                .fare(250.00)
                .departureAirport("HYD")
                .destinationAirport("DEL")
                .departureTime(LocalDateTime.now().plusHours(1))
                .arrivalTime(LocalDateTime.now().plusHours(2))
                .build();

        FlightModel flight2 = FlightModel.builder()
                .airline("Test Airline 2")
                .supplier("Test Supplier 2")
                .fare(300.00)
                .departureAirport("HYD")
                .destinationAirport("DEL")
                .departureTime(LocalDateTime.now().plusHours(1))
                .arrivalTime(LocalDateTime.now().plusHours(2))
                .build();

        List<FlightModel> flights = List.of(flight1, flight2);

        when(flightService.getAllFlights()).thenReturn(flights);

        mockMvc.perform(get("/api/flights"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].airline").value("Test Airline 1"))
                .andExpect(jsonPath("$[1].airline").value("Test Airline 2"));

        verify(flightService, times(1)).getAllFlights();
    }

    @Test
    void testGetFlightById() throws Exception {
        Long validId = 1L;
        FlightModel flight = FlightModel.builder()
                .airline("Test Airline")
                .supplier("Test Supplier")
                .fare(300.00)
                .departureAirport("HYD")
                .destinationAirport("DEL")
                .departureTime(LocalDateTime.now().plusHours(1))
                .arrivalTime(LocalDateTime.now().plusHours(2))
                .build();

        when(flightService.getFlightById(validId)).thenReturn(flight);

        mockMvc.perform(get("/api/flights/{id}", validId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.airline").value("Test Airline"))
                .andExpect(jsonPath("$.fare").value(300.00));

        verify(flightService, times(1)).getFlightById(validId);
    }


}
