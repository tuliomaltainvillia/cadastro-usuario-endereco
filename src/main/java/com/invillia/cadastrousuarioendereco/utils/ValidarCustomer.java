package com.invillia.cadastrousuarioendereco.utils;

import com.invillia.cadastrousuarioendereco.entity.Customer;

public class ValidarCustomer {

    private Customer customer;

    public ValidarCustomer(Customer customer) {
        this.customer = customer;
    }

    private void validarCpfCnpj() throws Exception {
        if (this.customer.getTipo().equals("PF")) {
            String cpf = this.customer.getCpf_cnpj().replaceAll("\\.", "").replaceAll("-", "");
            if (cpf.length() != 11) {
                throw new Exception("CPF inválido");
            }
        } else {
            String cnpj = this.customer.getCpf_cnpj().replaceAll("\\.", "").replaceAll("/", "");
            if (cnpj.length() != 14) {
                throw new Exception("CNPJ inválido");
            }
        }
    }

    private void validarEmail() throws Exception {
        String email = this.customer.getEmail();
        if (!email.contains("@")) {
            throw new Exception("Email inválido");
        }
    }

    private void validarTipo() throws Exception {
        String tipo = this.customer.getTipo();
        if (!"PJ".equals(tipo) && !"PF".equals(tipo)) {
            throw new Exception("Tipo inválido");
        }
    }


    private void validarTelefone() throws Exception {
        String telefone = this.customer.getTelefone().replaceAll("\\(", "").replaceAll("\\)", "")
                .replaceAll("-", "").replaceAll(" ", "");
        if (telefone.length() < 10 || telefone.length() > 11) {
            throw new Exception("Telefone inválido");
        }
    }

    public void validar() throws Exception {
        validarTelefone();
        validarTipo();
        validarEmail();
        validarCpfCnpj();
    }
}
