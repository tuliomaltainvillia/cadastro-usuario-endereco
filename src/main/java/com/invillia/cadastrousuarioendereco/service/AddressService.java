package com.invillia.cadastrousuarioendereco.service;

import com.invillia.cadastrousuarioendereco.entity.Address;
import com.invillia.cadastrousuarioendereco.repository.AddressRepository;
import com.invillia.cadastrousuarioendereco.utils.ValidarAddress;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        try {
            ValidarAddress validarAddress = new ValidarAddress(address);
            validarAddress.validar();
            UUID uuid = UUID.randomUUID();
            address.setIdAddres(uuid);
            return ResponseEntity.ok(addressRepository.save(address));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity buscarEndereco(Integer ultimoIndice,Integer totalPorPagina, String filtro) {
        try {
            Pageable pageable;
            if(filtro.isEmpty()){
                pageable = PageRequest.of(ultimoIndice, totalPorPagina);
            } else {
                pageable = PageRequest.of(ultimoIndice, totalPorPagina, Sort.by(filtro));
            }
            List<Address> addressList = addressRepository.findAll(pageable).stream().toList();
            if (addressList.size() > 0) {
                return ResponseEntity.ok(addressList);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity buscarUnicoEndereco(UUID uuid) {
        try {
            Address address = addressRepository.findById(uuid).orElse(null);
            if (null == address) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(address);
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity editarAddress(UUID uuid, Address address) {
        try {
            Address address1 = addressRepository.save(address);
            return ResponseEntity.ok(address1);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    public ResponseEntity deletarAddress(UUID uuid) {
        addressRepository.deleteById(uuid);
        Address address = addressRepository.findById(uuid).orElse(null);
        if (null == address) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(304).build();
        }
    }
}
