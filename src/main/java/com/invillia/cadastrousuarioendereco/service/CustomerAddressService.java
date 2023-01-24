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

    public ResponseEntity salvar(UUID idCustomer, UUID idAddress, Boolean principal) {
        try{
            UUID uuid = UUID.randomUUID();
            CustomerAddress customerAddress = new CustomerAddress();
            customerAddress.setIdCustomerAddress(uuid);
            customerAddress.setIdCustomer(idCustomer);
            customerAddress.setIdAddress(idAddress);
            customerAddress.setPrincipal(principal);
            return ResponseEntity.ok(customerAddressRepository.save(customerAddress));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e);
        }
    }


    public ResponseEntity inserirVarios(List<CustomerAddress> customerAddressList) {
        try {
            return ResponseEntity.ok(customerAddressRepository.saveAll(customerAddressList));
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    public ResponseEntity buscarTodos(Integer ultimoIndice, Integer totalPorPagina, String filtro) {

        try {
            Pageable pageable = PageRequest.of(ultimoIndice, totalPorPagina);;

            List<CustomerAddress> customerAddressList = customerAddressRepository.buscarPaginado(filtro, pageable).stream().toList();
            return ResponseEntity.ok(customerAddressList);
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e);
        }
    }

    public ResponseEntity findAll(){
        try {
            return ResponseEntity.ok(customerAddressRepository.findAll());
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e);
        }
    }
}
