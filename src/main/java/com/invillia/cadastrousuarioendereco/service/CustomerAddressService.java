package com.invillia.cadastrousuarioendereco.service;

import com.invillia.cadastrousuarioendereco.entity.CustomerAddress;
import com.invillia.cadastrousuarioendereco.repository.CustomerAddressRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerAddressService {

    private CustomerAddressRepository customerAddressRepository;

    public CustomerAddressService(CustomerAddressRepository customerAddressRepository) {
        this.customerAddressRepository = customerAddressRepository;
    }

    public CustomerAddress salvar(UUID idCustomer, UUID idAddress, Boolean principal) {
        UUID uuid = UUID.randomUUID();
        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setIdCustomerAddress(uuid);
        customerAddress.setIdCustomer(idCustomer);
        customerAddress.setIdAddress(idAddress);
        customerAddress.setPrincipal(principal);
        return customerAddressRepository.save(customerAddress);
    }


    public List<CustomerAddress> inserirVarios(List<CustomerAddress> customerAddressList) {
        return customerAddressRepository.saveAll(customerAddressList);
    }

    public ResponseEntity buscarTodos(Integer ultimoIndice, Integer totalPorPagina, String filtro) {

        Pageable pageable;
        if(filtro.isEmpty()){
            pageable = PageRequest.of(ultimoIndice, totalPorPagina);
        } else {
            pageable = PageRequest.of(ultimoIndice, totalPorPagina, Sort.by(filtro));
        }
        List<CustomerAddress> customerAddressList = customerAddressRepository.findAll(pageable).stream().toList();
        return ResponseEntity.ok(customerAddressList);
    }
}
