package com.invillia.cadastrousuarioendereco.controller;

import com.invillia.cadastrousuarioendereco.entity.Customer;
import com.invillia.cadastrousuarioendereco.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @Mock
    private CustomerService service;

    @InjectMocks
    private CustomerController controller;

    @Test
    public void teste_se_o_metodo_salvar_retorna_status_200_quando_argumento_valido_e_passado(){
        UUID idAddress = UUID.randomUUID();
        Customer customer = new Customer();

        Mockito.when(service.salvar(customer,idAddress)).thenReturn(ResponseEntity.ok(customer));

        ResponseEntity response = controller.salvar(customer,idAddress);

        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_salvar_retorna_null_quando_argumento_invalido_e_passado(){
        ResponseEntity response = controller.salvar(null, null);

        Assertions.assertEquals(null, response);
    }

    @Test
    public void teste_se_o_metodo_buscar_retorna_status_200_quando_argumento_valido_e_passado(){
        Customer customer = new Customer();

        Mockito.when(service.buscar(0,10,"")).thenReturn(ResponseEntity.ok(customer));

        ResponseEntity response = controller.buscar(0,10,"");

        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_buscar_retorna_null_quando_argumento_invalido_e_passado(){
        ResponseEntity response = controller.buscar(null, null, null);

        Assertions.assertEquals(null, response);
    }

    @Test
    public void teste_se_o_metodo_buscarUnico_retorna_status_200_quando_argumento_valido_e_passado(){
        UUID uuid = UUID.randomUUID();
        Customer customer = new Customer();

        Mockito.when(service.buscarUnico(uuid)).thenReturn(ResponseEntity.ok(customer));

        ResponseEntity response = controller.buscarUnico(uuid);

        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_buscarUnico_retorna_null_quando_argumento_invalido_e_passado(){
        ResponseEntity response = controller.buscarUnico(null);

        Assertions.assertEquals(null, response);
    }

    @Test
    public void teste_se_o_metodo_editarCustomer_retorna_status_200_quando_argumento_valido_e_passado(){
        UUID uuid = UUID.randomUUID();
        Customer customer = new Customer();

        Mockito.when(service.editarCustomer(uuid, customer)).thenReturn(ResponseEntity.ok(customer));

        ResponseEntity response = controller.editarCustomer(uuid, customer);

        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_editarCustomer_retorna_null_quando_argumento_invalido_e_passado(){
        ResponseEntity response = controller.editarCustomer(null, null);

        Assertions.assertEquals(null, response);
    }

    @Test
    public void teste_se_o_metodo_deletarCustomer_retorna_status_200_quando_argumento_valido_e_passado(){
        UUID uuid = UUID.randomUUID();
        Customer customer = new Customer();

        Mockito.when(service.deletarCustomer(uuid)).thenReturn(ResponseEntity.ok(customer));

        ResponseEntity response = controller.deletarCustomer(uuid);

        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_deletarCustomer_retorna_null_quando_argumento_invalido_e_passado(){
        ResponseEntity response = controller.deletarCustomer(null);

        Assertions.assertEquals(null, response);
    }

    @Test
    public void teste_se_o_metodo_vincularEndereco_retorna_status_200_quando_argumento_valido_e_passado(){
        UUID idCustomer = UUID.randomUUID();
        UUID idAddress = UUID.randomUUID();

        Customer customer = new Customer();

        Mockito.when(service.vincularEndereco(idCustomer, idAddress)).thenReturn(ResponseEntity.ok(customer));

        ResponseEntity response = controller.vincularEndereco(idCustomer, idAddress);

        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_vincularEndereco_retorna_null_quando_argumento_invalido_e_passado(){
        ResponseEntity response = controller.vincularEndereco(null, null);

        Assertions.assertEquals(null, response);
    }

    @Test
    public void teste_se_o_metodo_tonarPrincipal_retorna_status_200_quando_argumento_valido_e_passado(){
        UUID idCustomer = UUID.randomUUID();
        UUID idAddress = UUID.randomUUID();

        Customer customer = new Customer();

        Mockito.when(service.tornarPrincipal(idCustomer,idAddress)).thenReturn(ResponseEntity.ok(customer));

        ResponseEntity response = controller.tonarPrincipal(idCustomer, idAddress);

        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_tonarPrincipal_retorna_null_quando_argumento_invalido_e_passado(){
        ResponseEntity response = controller.tonarPrincipal(null, null);

        Assertions.assertEquals(null, response);
    }
}
