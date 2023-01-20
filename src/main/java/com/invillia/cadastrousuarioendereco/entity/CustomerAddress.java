package com.invillia.cadastrousuarioendereco.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "CUSTOMERADDRESS")
public class CustomerAddress {

    @Id
    @Column(name = "IDCUSTOMERADDRESS")
    private UUID idCustomerAddress;

    @Column(name = "IDCUSTOMER")
    private UUID idCustomer;

    @Column(name = "IDADDRESS")
    private UUID idAddress;

    @Column(name = "PRINCIPAL")
    private Boolean principal;

    public UUID getIdCustomerAddress() {
        return idCustomerAddress;
    }

    public void setIdCustomerAddress(UUID idCustomerAddress) {
        this.idCustomerAddress = idCustomerAddress;
    }

    public UUID getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(UUID idCustomer) {
        this.idCustomer = idCustomer;
    }

    public UUID getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(UUID idAddress) {
        this.idAddress = idAddress;
    }

    public Boolean getPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }
}
