package com.invillia.cadastrousuarioendereco.controller;

import com.invillia.cadastrousuarioendereco.entity.CustomerAddress;
import com.invillia.cadastrousuarioendereco.service.CustomerAddressService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CustomerAddressControllerTest {

    @Mock
    private CustomerAddressService service;

    @InjectMocks
    private CustomerAddressController controller;

    @Test
    public void teste_se_o_metodo_buscarTodos_retorna_status_200_quando_argumento_valido_e_passado(){
        List<CustomerAddress> list = new ArrayList<>();
        Mockito.when(service.buscarTodos(0,10,"")).thenReturn(ResponseEntity.ok(list));

        ResponseEntity response = controller.buscarTodos(0,10,"");

        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_buscarTodos_retorna_null_quando_argumento_invalido_e_passado(){
        ResponseEntity response = controller.buscarTodos(null,null,null);

        Assertions.assertEquals(null, response);
    }
}
