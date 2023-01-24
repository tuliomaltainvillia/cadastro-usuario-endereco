package com.invillia.cadastrousuarioendereco.controller;

import com.invillia.cadastrousuarioendereco.entity.Address;
import com.invillia.cadastrousuarioendereco.service.AddressService;
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
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class AddressControllerTest {

    @Mock
    private AddressService service;

    @InjectMocks
    private AddressControler controler;

    @Test
    public void teste_se_o_metodo_salvarEndereco_retorna_status_200_quando_argumento_valido_e_passado(){
        Address address = new Address();
        address.setEstado("ESTADO_TESTE");
        address.setRua("RUA_TESTE");
        address.setNumero("30");
        address.setCidade("CIDADE_TESTE");
        address.setCep("38300000");
        address.setBairro("BAIRRO_TESTE");

        Mockito.when(service.salvarEndereco(address)).thenReturn(ResponseEntity.ok(address));

        ResponseEntity response = controler.salvarEndereco(address);

        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_salvarEndereco_retorna_null_quando_argumento_invalido_e_passado(){
        ResponseEntity response = controler.salvarEndereco(null);

        Assertions.assertEquals(null, response);
    }

    @Test
    public void teste_se_o_metodo_buscarEnderecos_retorna_status_200_quando_argumento_valido_e_passado(){
        List<Address> list = new ArrayList<>();
        list.addAll(List.of(new Address(), new Address()));

        Mockito.when(service.buscarEndereco(0,10,"")).thenReturn(ResponseEntity.ok(list));

        ResponseEntity response = controler.buscarEnderecos(0,10,"");

        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_buscarEnderecos_retorna_null_quando_argumento_invalido_e_passado(){
        ResponseEntity response = controler.buscarEnderecos(null, null, null);

        Assertions.assertEquals(null, response);
    }

    @Test
    public void teste_se_o_metodo_buscarUnicoEndereco_retorna_status_200_quando_argumento_valido_e_passado(){
        UUID uuid = UUID.randomUUID();
        Address address = new Address();

        Mockito.when(service.buscarUnicoEndereco(uuid)).thenReturn(ResponseEntity.ok(address));

        ResponseEntity response = controler.buscarUnicoEndereco(uuid);

        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_buscarUnicoEndereco_retorna_null_quando_argumento_invalido_e_passado(){
        ResponseEntity response = controler.buscarUnicoEndereco(null);

        Assertions.assertEquals(null, response);
    }

    @Test
    public void teste_se_o_metodo_editarAddress_retorna_status_200_quando_argumento_valido_e_passado(){
        UUID uuid = UUID.randomUUID();
        Address address = new Address();

        Mockito.when(service.editarAddress(uuid,address)).thenReturn(ResponseEntity.ok(address));

        ResponseEntity response = controler.editarAddress(uuid,address);

        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_editarAddress_retorna_null_quando_argumento_invalido_e_passado(){
        ResponseEntity response = controler.editarAddress(null, null);

        Assertions.assertEquals(null, response);
    }

    @Test
    public void teste_se_o_metodo_deletarAddress_retorna_status_200_quando_argumento_valido_e_passado(){
        UUID uuid = UUID.randomUUID();

        Mockito.when(service.deletarAddress(uuid)).thenReturn(ResponseEntity.ok().build());

        ResponseEntity response = controler.deletarAddress(uuid);

        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_deletarAddress_retorna_null_quando_argumento_invalido_e_passado(){
        ResponseEntity response = controler.deletarAddress(null);

        Assertions.assertEquals(null, response);
    }
}
