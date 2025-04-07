package com.flight.management.Worldline_Assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/database")
public class DatabaseConnectionController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/dbcheck")
    public ResponseEntity<String> checkDatabaseConnection() {
        try (Connection connection = dataSource.getConnection()) {

            return ResponseEntity.ok("Database is up and running");

        } catch (SQLException e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error connecting to the database " + e.getMessage());
        }
    }
}
