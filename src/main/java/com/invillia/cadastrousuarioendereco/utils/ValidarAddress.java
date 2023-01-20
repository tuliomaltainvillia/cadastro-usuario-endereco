package com.invillia.cadastrousuarioendereco.utils;

import com.invillia.cadastrousuarioendereco.entity.Address;

public class ValidarAddress {

    private Address address;

    public ValidarAddress(Address address) {
        this.address = address;
    }

    private void validarRua() throws Exception{
        String rua = this.address.getRua();
        if (rua.length() < 5) {
            throw new Exception("Logradouro Inválido.");
        }
    }

    private void validarNumero() throws Exception{
        String numero = this.address.getNumero();
        if (numero.isEmpty()) {
            throw new Exception("Número não inserido.");
        }
    }

    private void validarBairro() throws Exception{
        String bairro = this.address.getBairro();
        if (bairro.length() < 3) {
            throw new Exception("Bairro Inválido.");
        }
    }

    private void validarCidade() throws Exception{
        String cidade = this.address.getCidade();
        if (cidade.length() < 3) {
            throw new Exception("Cidade inválida.");
        }
    }

    private void validarCep() throws Exception{
        String cep = this.address.getCep().replaceAll("\\.", "").replaceAll("-", "");
        if (cep.length() != 8) {
            throw new Exception("CEP inválido.");
        }
    }

    private void validarEstado() throws Exception{
        String estado = this.address.getEstado();
        if (estado.length() < 4) {
            throw new Exception("Estado inválido.");
        }
    }

    public void validar() throws Exception{
        validarRua();
        validarNumero();
        validarBairro();
        validarCidade();
        validarCep();
        validarEstado();
    }
}
