package com.flight.management.Worldline_Assignment.service;


import com.flight.management.Worldline_Assignment.config.FeignErrorConfig;
import com.flight.management.Worldline_Assignment.dto.CrazySupplierRequestDTO;
import com.flight.management.Worldline_Assignment.dto.CrazySupplierResponseDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "${crazy.supplier.name}", url = "${crazy.supplier.url}", configuration = FeignErrorConfig.class)
public interface CrazySupplierClients {

    @PostMapping("/flights")
    List<CrazySupplierResponseDTO> getFlights( @Valid @RequestBody CrazySupplierRequestDTO request);
}