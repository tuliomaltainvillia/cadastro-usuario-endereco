package com.invillia.cadastrousuarioendereco.controller;

import com.invillia.cadastrousuarioendereco.service.CustomerAddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerAddressController {

    private CustomerAddressService customerAddressService;

    public CustomerAddressController(CustomerAddressService customerAddressService) {
        this.customerAddressService = customerAddressService;
    }

    @GetMapping("/all")
    public ResponseEntity buscarTodos(
            @RequestHeader(value = "ultimoIndice") Integer ultimoIndice,
            @RequestHeader(value = "totalPorPagina") Integer totalPorPagina,
            @RequestHeader(value = "filtro") String filtro
    ) {
        return customerAddressService.buscarTodos(ultimoIndice, totalPorPagina, filtro);
    }
}
