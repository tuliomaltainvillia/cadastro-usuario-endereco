package com.invillia.cadastrousuarioendereco.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "CUSTOMER")
public class Customer {
    @Id
    @Column(name = "IDCUSTOMER")
    private UUID idCustomer;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "CPF_CNPJ")
    private String cpf_cnpj;

    @Column(name = "TIPO")
    private String tipo;

    @Column(name = "TELEFONE")
    private String telefone;


    public UUID getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(UUID idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf_cnpj() {
        return cpf_cnpj;
    }

    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}