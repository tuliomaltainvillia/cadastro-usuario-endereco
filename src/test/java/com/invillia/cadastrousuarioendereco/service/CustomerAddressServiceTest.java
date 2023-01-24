package com.invillia.cadastrousuarioendereco.service;

import com.invillia.cadastrousuarioendereco.entity.CustomerAddress;
import com.invillia.cadastrousuarioendereco.repository.CustomerAddressRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class CustomerAddressServiceTest {

    @Mock
    private CustomerAddressRepository repository;

    @InjectMocks
    private CustomerAddressService service;

    @Test
    public void teste_se_o_metodo_salvar_retorna_status_200_quando_um_registro_e_salvo_com_sucesso_no_banco_de_dados(){
        UUID idCustomer = UUID.randomUUID();
        UUID idAddress = UUID.randomUUID();

        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setIdCustomerAddress(UUID.randomUUID());
        customerAddress.setIdCustomer(idCustomer);
        customerAddress.setIdAddress(idAddress);
        customerAddress.setPrincipal(false);

        Mockito.when(repository.save(Mockito.any())).thenReturn(customerAddress);

        ResponseEntity response = service.salvar(idCustomer, idAddress, false);

        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_salvar_retorna_status_500_quando_a_conexao_com_o_banco_de_dados_falha(){
        UUID idCustomer = UUID.randomUUID();
        UUID idAddress = UUID.randomUUID();

        Mockito.when(repository.save(Mockito.any())).thenThrow(RuntimeException.class);

        ResponseEntity response = service.salvar(idCustomer, idAddress, false);

        Assertions.assertEquals(500, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_inserirVarios_retorna_status_200_quando_varios_registros_sao_inseridos_no_banco(){
        List<CustomerAddress> list = new ArrayList<>();
        list.addAll(List.of(new CustomerAddress(), new CustomerAddress()));

        Mockito.when(repository.saveAll(list)).thenReturn(list);

        ResponseEntity response = service.inserirVarios(list);

        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_inserirVarios_retorna_status_500_quando_a_conexao_com_banco_de_dados_falha(){
        List<CustomerAddress> list = new ArrayList<>();
        list.addAll(List.of(new CustomerAddress(), new CustomerAddress()));

        Mockito.when(repository.saveAll(list)).thenThrow(RuntimeException.class);

        ResponseEntity response = service.inserirVarios(list);

        Assertions.assertEquals(500, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_buscarTodos_retorna_status_200_quando_consegue_buscar_todos_os_registros(){
        List<CustomerAddress> list = new ArrayList<>();
        list.addAll(List.of(new CustomerAddress(), new CustomerAddress()));

        Pageable pageable = PageRequest.of(0,10);

        Page<CustomerAddress> page = new PageImpl<>(list);

        Mockito.when(repository.buscarPaginado("", pageable)).thenReturn(page);

        ResponseEntity response = service.buscarTodos(0,10,"");

        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_buscarTodos_retorna_status_500_quando_a_conexao_com_o_banco_de_dados_falha(){
        List<CustomerAddress> list = new ArrayList<>();
        list.addAll(List.of(new CustomerAddress(), new CustomerAddress()));

        Pageable pageable = PageRequest.of(0,10);

        Mockito.when(repository.buscarPaginado("", pageable)).thenThrow(RuntimeException.class);

        ResponseEntity response = service.buscarTodos(0,10,"");

        Assertions.assertEquals(500, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_findAll_retorna_status_200_quando_registros_sao_encontrados(){
        List<CustomerAddress> list = new ArrayList<>();
        list.addAll(List.of(new CustomerAddress(), new CustomerAddress()));

        Mockito.when(repository.findAll()).thenReturn(list);

        ResponseEntity response = service.findAll();

        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_findAll_retorna_status_500_quando_a_conexao_com_o_banco_de_dados_falha(){
        List<CustomerAddress> list = new ArrayList<>();
        list.addAll(List.of(new CustomerAddress(), new CustomerAddress()));

        Mockito.when(repository.findAll()).thenThrow(RuntimeException.class);

        ResponseEntity response = service.findAll();

        Assertions.assertEquals(500, response.getStatusCode().value());
    }
}
