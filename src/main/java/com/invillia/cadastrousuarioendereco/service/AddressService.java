package com.invillia.cadastrousuarioendereco.service;

import com.invillia.cadastrousuarioendereco.entity.Address;
import com.invillia.cadastrousuarioendereco.repository.AddressRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AddressService {

    private AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public ResponseEntity salvarEndereco(Address address) {
        if (address.getBairro().isEmpty() || address.getCep().isEmpty() || address.getCidade().isEmpty() ||
        address.getEstado().isEmpty() || address.getNumero().isEmpty() || address.getRua().isEmpty()) {
            return ResponseEntity.status(400).build();
        }
        UUID uuid = UUID.randomUUID();
        address.setIdAddres(uuid);
        return ResponseEntity.ok(addressRepository.save(address));
    }

    public List<Address> buscarEndereco() {
        return addressRepository.findAll();
    }

    public Address buscarUnicoEndereco(UUID uuid) {
        return addressRepository.findById(uuid).orElse(null);
    }

    public Address editarAddress(UUID uuid, Address address) {
        address.setIdAddres(uuid);
        return addressRepository.save(address);
    }

    public Boolean deletarAddress(UUID uuid) {
        Boolean deletouAddress = false;
        addressRepository.deleteById(uuid);
        Address address = addressRepository.findById(uuid).orElse(null);
        if (null == address) {
            deletouAddress = true;
        }

        return deletouAddress;
    }
}
