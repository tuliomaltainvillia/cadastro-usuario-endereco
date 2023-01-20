package com.invillia.cadastrousuarioendereco.controller;

import com.invillia.cadastrousuarioendereco.entity.Customer;
import com.invillia.cadastrousuarioendereco.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity salvar(@RequestBody Customer customer, @RequestHeader(value = "idAddress") UUID idAddress) {
        return customerService.salvar(customer, idAddress);
    }

    @GetMapping("/buscar")
    public List<Customer> buscar() {
        return customerService.buscar();
    }

    @GetMapping("/buscarunico/{id}")
    public Customer buscarUnico(@PathVariable("id") UUID uuid) {
        return customerService.buscarUnico(uuid);
    }

    @PutMapping("/editar/{id}")
    public Customer editarCustomer(@PathVariable("id") UUID uuid, @RequestBody Customer customer) {
        return customerService.editarCustomer(uuid, customer);
    }

    @DeleteMapping("/deletar/{id}")
    public Boolean deletarCustomer(@PathVariable("id") UUID uuid) {
        return customerService.deletarCustomer(uuid);
    }

    @PostMapping("/vincularendereco/{id}")
    public ResponseEntity vincularEndereco(@PathVariable("id") UUID idCustomer, @RequestHeader(value = "idAddress") UUID idAddress) {
        return customerService.vincularEndereco(idCustomer, idAddress);
    }

    @PutMapping("/tonarprincipal/{id}")
    public ResponseEntity tonarPrincipal(@PathVariable("id") UUID idCustomer, @RequestHeader(value = "idAddress") UUID idAddress) {
        return customerService.tornarPrincipal(idCustomer, idAddress);
    }
}
