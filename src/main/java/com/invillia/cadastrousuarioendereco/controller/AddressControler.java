package com.invillia.cadastrousuarioendereco.controller;

import com.invillia.cadastrousuarioendereco.entity.Address;
import com.invillia.cadastrousuarioendereco.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class AddressControler {

    private AddressService addressService;

    public AddressControler(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/cadastrarendereco")
    public ResponseEntity salvarEndereco(@RequestBody Address address) {
        return addressService.salvarEndereco(address);
    }

    @GetMapping("/buscarenderecos")
    public ResponseEntity buscarEnderecos(
            @RequestHeader(value = "ultimoIndice") Integer ultimoIndice,
            @RequestHeader(value = "totalPorPagina") Integer totalPorPagina,
            @RequestHeader(value = "filtro") String filtro
    ) {
        return addressService.buscarEndereco(ultimoIndice,totalPorPagina,filtro);
    }

    @GetMapping("/buscarunicoendereco/{id}")
    public ResponseEntity buscarUnicoEndereco(@PathVariable("id")UUID uuid) {
        return addressService.buscarUnicoEndereco(uuid);
    }

    @PutMapping("/editarendereco/{id}")
    public ResponseEntity editarAddress(@PathVariable("id") UUID uuid, @RequestBody Address address) {
        return addressService.editarAddress(uuid, address);
    }

    @DeleteMapping("/deletarendereco/{id}")
    public ResponseEntity deletarAddress(@PathVariable("id") UUID uuid) {
        return addressService.deletarAddress(uuid);
    }
}
