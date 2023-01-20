package com.invillia.cadastrousuarioendereco.service;

import com.invillia.cadastrousuarioendereco.entity.Address;
import com.invillia.cadastrousuarioendereco.entity.Customer;
import com.invillia.cadastrousuarioendereco.entity.CustomerAddress;
import com.invillia.cadastrousuarioendereco.repository.CustomerRepository;
import com.invillia.cadastrousuarioendereco.utils.ValidarCustomer;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    private AddressService addressService;

    private CustomerAddressService customerAddressService;

    public CustomerService(CustomerRepository customerRepository, AddressService addressService, CustomerAddressService customerAddressService) {
        this.customerRepository = customerRepository;
        this.addressService = addressService;
        this.customerAddressService = customerAddressService;
    }

    public ResponseEntity salvar(Customer customer, UUID idAddress) {
        try {
            ValidarCustomer validarCustomer = new ValidarCustomer(customer);
            validarCustomer.validar();
            Address addressExist = (Address) addressService.buscarUnicoEndereco(idAddress).getBody();
            if (null != addressExist) {
                UUID uuid = UUID.randomUUID();
                customer.setIdCustomer(uuid);
                Customer customer1 = customerRepository.save(customer);
                CustomerAddress customerAddress = customerAddressService.salvar(customer1.getIdCustomer(), idAddress, true);
                return ResponseEntity.ok(customer1);
            } else {
                return ResponseEntity.status(406).build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    public ResponseEntity buscar(Integer ultimoIndice, Integer totalPorPagina, String filtro) {
        try {
            Pageable pageable;
            if(filtro.isEmpty()){
                pageable = PageRequest.of(ultimoIndice, totalPorPagina);
            } else {
                pageable = PageRequest.of(ultimoIndice, totalPorPagina, Sort.by(filtro));
            }
            List<Customer> customerList = customerRepository.findAll(pageable).stream().toList();
            if (customerList.size() > 0) {
                return ResponseEntity.ok(customerList);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity buscarUnico(UUID uuid) {
        try {
            Customer customer = customerRepository.findById(uuid).orElse(null);
            if (null == customer) {
                return ResponseEntity.ok(customer);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity editarCustomer(UUID uuid, Customer customer) {
        try {
            customer.setIdCustomer(uuid);
            Customer customer1 = customerRepository.save(customer);
            return ResponseEntity.ok(customer1);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity deletarCustomer(UUID uuid) {
        customerRepository.deleteById(uuid);
        Customer customer = customerRepository.findById(uuid).orElse(null);
        if (null == customer) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(304).build();
        }
    }

    public ResponseEntity vincularEndereco(UUID idCustomer, UUID idAddress) {
        try {
            Address addressExist = (Address) addressService.buscarUnicoEndereco(idAddress).getBody();
            List<CustomerAddress> customerAddressList = (List<CustomerAddress>) customerAddressService.findAll().getBody();
            List<CustomerAddress> customers = new ArrayList<>();
            for (int i = 0; i < customerAddressList.size(); i++) {
                if (customerAddressList.get(i).getIdCustomer().equals(idCustomer)) {
                    customers.add(customerAddressList.get(i));
                }
            }
            if (customers.size() >= 5) {
                return ResponseEntity.status(401).build();
            }
            if (null != addressExist) {
                CustomerAddress customerAddress = customerAddressService.salvar(idCustomer, idAddress, false);
                return ResponseEntity.ok(customerAddress);
            } else {
                return ResponseEntity.status(406).build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity tornarPrincipal(UUID idCustomer, UUID idAddress) {
        try {
            List<CustomerAddress> customerAddressList = (List<CustomerAddress>) customerAddressService.findAll().getBody();
            if (customerAddressList.size() > 0) {
                for (int i = 0; i < customerAddressList.size(); i++) {
                    if (customerAddressList.get(i).getIdCustomer().equals(idCustomer)) {
                        if (customerAddressList.get(i).getIdAddress().equals(idAddress)) {
                            customerAddressList.get(i).setPrincipal(true);
                        } else {
                            customerAddressList.get(i).setPrincipal(false);
                        }
                    }
                }
                customerAddressService.inserirVarios(customerAddressList);
                return ResponseEntity.ok(customerAddressList);
            } else {
                return ResponseEntity.status(304).build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}