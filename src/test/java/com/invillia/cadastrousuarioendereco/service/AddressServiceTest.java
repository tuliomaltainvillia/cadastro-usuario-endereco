package com.invillia.cadastrousuarioendereco.service;

import com.invillia.cadastrousuarioendereco.entity.Address;
import com.invillia.cadastrousuarioendereco.repository.AddressRepository;
import com.invillia.cadastrousuarioendereco.response.ViaCepResponse;
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
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {
    @Mock
    private AddressRepository repository;
    @Mock
    private ViaCepService viaCepService;

    @InjectMocks
    private AddressService service;

    @Test
    public void teste_se_metodo_salvarEndereco_retorna_status_200_quando_um_objeto_valido_e_passado(){
        Address address = new Address();
        address.setEstado("ESTADO_TESTE");
        address.setRua("RUA_TESTE");
        address.setNumero("30");
        address.setCidade("CIDADE_TESTE");
        address.setCep("38300000");
        address.setBairro("BAIRRO_TESTE");
        address.setIdAddress(UUID.randomUUID());

        ViaCepResponse viaCepResponse = new ViaCepResponse();
        viaCepResponse.setCep("38300000");

        Mockito.when(repository.save(address)).thenReturn(address);
        Mockito.when(viaCepService.call("38300000")).thenReturn(viaCepResponse);

        ResponseEntity response = service.salvarEndereco(address);

        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void teste_se_metodo_salvarEndereco_retorna_status_500_se_nao_encontrar_o_cep_no_via_cep(){
        Address address = new Address();
        address.setEstado("ESTADO_TESTE");
        address.setRua("RUA_TESTE");
        address.setNumero("30");
        address.setCidade("CIDADE_TESTE");
        address.setCep("38300000");
        address.setBairro("BAIRRO_TESTE");
        address.setIdAddress(UUID.randomUUID());

        ViaCepResponse viaCepResponse = new ViaCepResponse();

        Mockito.when(viaCepService.call("38300000")).thenReturn(viaCepResponse);

        ResponseEntity response = service.salvarEndereco(address);

        Assertions.assertEquals(500, response.getStatusCode().value());
    }

    @Test
    public void teste_se_metodo_salvarEndereco_retorna_um_body_valido_quando_um_objeto_valido_e_passado(){
        Address address = new Address();
        address.setEstado("ESTADO_TESTE");
        address.setRua("RUA_TESTE");
        address.setNumero("30");
        address.setCidade("CIDADE_TESTE");
        address.setCep("38300000");
        address.setBairro("BAIRRO_TESTE");

        ViaCepResponse viaCepResponse = new ViaCepResponse();
        viaCepResponse.setCep("38300000");

        Mockito.when(viaCepService.call("38300000")).thenReturn(viaCepResponse);

        Mockito.when(repository.save(address)).thenReturn(address);

        Address response = (Address) service.salvarEndereco(address).getBody();

        Assertions.assertEquals("ESTADO_TESTE", response.getEstado());
        Assertions.assertEquals("RUA_TESTE", response.getRua());
        Assertions.assertEquals("30", response.getNumero());
        Assertions.assertEquals("CIDADE_TESTE", response.getCidade());
        Assertions.assertEquals("38300000", response.getCep());
        Assertions.assertEquals("BAIRRO_TESTE", response.getBairro());
        Assertions.assertNotNull(response.getIdAddress());
    }

    @Test
    public void teste_se_metodo_salvarEndereco_retorna_status_500_quando_a_conexao_com_o_banco_falha(){
        Address address = new Address();
        address.setEstado("ESTADO_TESTE");
        address.setRua("RUA_TESTE");
        address.setNumero("30");
        address.setCidade("CIDADE_TESTE");
        address.setCep("38300000");
        address.setBairro("BAIRRO_TESTE");
        address.setIdAddress(UUID.randomUUID());

        Mockito.when(viaCepService.call("38300000")).thenThrow(RuntimeException.class);

        ResponseEntity response = service.salvarEndereco(address);

        Assertions.assertEquals(500, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_medoto_buscarEndereco_retorna_status_200_quando_um_objeto_valido_e_passado(){
        Integer ultimoIndice = 0;
        Integer totalPorPagina = 10;
        String filtro = "";

        Pageable pageable = PageRequest.of(ultimoIndice, totalPorPagina);

        List<Address> list = new ArrayList<>();
        Address[] addresses = {new Address(), new Address(), new Address(), new Address(), new Address()};
        list.addAll(List.of(addresses));

        Page<Address> page = new PageImpl<>(list, pageable, list.size());

        Mockito.when(repository.buscarPaginado(filtro,pageable)).thenReturn(page);

        ResponseEntity response = service.buscarEndereco(ultimoIndice,totalPorPagina,filtro);

        Assertions.assertEquals(200,response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_medoto_buscarEndereco_retorna_uma_lista_valida_quando_um_objeto_valido_e_passado(){
        Integer ultimoIndice = 0;
        Integer totalPorPagina = 10;
        String filtro = "";

        Pageable pageable = PageRequest.of(ultimoIndice, totalPorPagina);

        List<Address> list = new ArrayList<>();
        Address[] addresses = {new Address(), new Address(), new Address(), new Address(), new Address()};
        list.addAll(List.of(addresses));

        Page<Address> page = new PageImpl<>(list, pageable, list.size());

        Mockito.when(repository.buscarPaginado(filtro,pageable)).thenReturn(page);

        List<Address> response = (List<Address>) service.buscarEndereco(ultimoIndice,totalPorPagina,filtro).getBody();

        Assertions.assertEquals(5, response.size());
    }

    @Test
    public void teste_se_o_medoto_buscarEndereco_retorna_status_204_quando_nenhum_registro_e_encontrado(){
        Integer ultimoIndice = 0;
        Integer totalPorPagina = 10;
        String filtro = "";

        Pageable pageable = PageRequest.of(ultimoIndice, totalPorPagina);

        List<Address> list = new ArrayList<>();
        Address[] addresses = {new Address(), new Address(), new Address(), new Address(), new Address()};
        list.addAll(List.of(addresses));

        Page<Address> page = Page.empty();

        Mockito.when(repository.buscarPaginado(filtro,pageable)).thenReturn(page);

        ResponseEntity response = service.buscarEndereco(ultimoIndice,totalPorPagina,filtro);

        Assertions.assertEquals(204, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_medoto_buscarEndereco_retorna_status_500_quando_a_conexao_com_o_banco_falha(){
        Integer ultimoIndice = 0;
        Integer totalPorPagina = 10;
        String filtro = "";

        Pageable pageable = PageRequest.of(ultimoIndice, totalPorPagina);

        Mockito.when(repository.buscarPaginado(filtro,pageable)).thenThrow(RuntimeException.class);

        ResponseEntity response = service.buscarEndereco(ultimoIndice,totalPorPagina,filtro);

        Assertions.assertEquals(500,response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_buscarUnicoEndereco_retorna_status_200_quando_um_objeto_valido_e_passado(){
        UUID uuid = UUID.randomUUID();
        Address address = new Address();
        address.setEstado("ESTADO_TESTE");
        address.setRua("RUA_TESTE");
        address.setNumero("30");
        address.setCidade("CIDADE_TESTE");
        address.setCep("38300000");
        address.setBairro("BAIRRO_TESTE");

        Optional<Address> optional = Optional.of(address);

        Mockito.when(repository.findById(uuid)).thenReturn(optional);

        ResponseEntity response = service.buscarUnicoEndereco(uuid);

        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_buscarUnicoEndereco_retorna_status_204_quando_um_objeto_valido_e_passado_e_nao_existe_registro_no_banco(){
        UUID uuid = UUID.randomUUID();
        Address address = new Address();
        address.setEstado("ESTADO_TESTE");
        address.setRua("RUA_TESTE");
        address.setNumero("30");
        address.setCidade("CIDADE_TESTE");
        address.setCep("38300000");
        address.setBairro("BAIRRO_TESTE");

        Optional<Address> optional = Optional.empty();

        Mockito.when(repository.findById(uuid)).thenReturn(optional);

        ResponseEntity response = service.buscarUnicoEndereco(uuid);

        Assertions.assertEquals(204, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_buscarUnicoEndereco_retorna_status_500_quando_a_conexao_com_o_banco_falha(){
        UUID uuid = UUID.randomUUID();
        Address address = new Address();
        address.setEstado("ESTADO_TESTE");
        address.setRua("RUA_TESTE");
        address.setNumero("30");
        address.setCidade("CIDADE_TESTE");
        address.setCep("38300000");
        address.setBairro("BAIRRO_TESTE");

        Mockito.when(repository.findById(uuid)).thenThrow(RuntimeException.class);

        ResponseEntity response = service.buscarUnicoEndereco(null);

        Assertions.assertEquals(500, response.getStatusCode().value());
    }

    @Test
    public void teste_se_metodo_editarAddress_retorna_status_200_quando_um_objeto_valido_e_passado(){
        Address address = new Address();
        address.setEstado("ESTADO_TESTE");
        address.setRua("RUA_TESTE");
        address.setNumero("30");
        address.setCidade("CIDADE_TESTE");
        address.setCep("38300000");
        address.setBairro("BAIRRO_TESTE");

        UUID uuid = UUID.randomUUID();

        Mockito.when(repository.save(address)).thenReturn(address);

        ResponseEntity response = service.editarAddress(uuid,address);

        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void teste_se_metodo_editarAddress_retorna_um_body_valido_quando_um_objeto_valido_e_passado(){
        Address address = new Address();
        address.setEstado("ESTADO_TESTE");
        address.setRua("RUA_TESTE");
        address.setNumero("30");
        address.setCidade("CIDADE_TESTE");
        address.setCep("38300000");
        address.setBairro("BAIRRO_TESTE");
        UUID uuid = UUID.randomUUID();
        address.setIdAddress(uuid);


        Mockito.when(repository.save(address)).thenReturn(address);

        Address response = (Address) service.editarAddress(uuid,address).getBody();

        Assertions.assertEquals("ESTADO_TESTE", response.getEstado());
        Assertions.assertEquals("RUA_TESTE", response.getRua());
        Assertions.assertEquals("30", response.getNumero());
        Assertions.assertEquals("CIDADE_TESTE", response.getCidade());
        Assertions.assertEquals("38300000", response.getCep());
        Assertions.assertEquals("BAIRRO_TESTE", response.getBairro());
        Assertions.assertNotNull(response.getIdAddress());
    }

    @Test
    public void teste_se_metodo_editarAddress_retorna_status_500_quando_a_conexao_com_o_banco_falha(){
        Address address = new Address();
        address.setEstado("ESTADO_TESTE");
        address.setRua("RUA_TESTE");
        address.setNumero("30");
        address.setCidade("CIDADE_TESTE");
        address.setCep("38300000");
        address.setBairro("BAIRRO_TESTE");
        UUID uuid = UUID.randomUUID();
        address.setIdAddress(uuid);

        Mockito.when(repository.save(address)).thenThrow(RuntimeException.class);

        ResponseEntity response = service.editarAddress(uuid,address);

        Assertions.assertEquals(500, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_deletarAddress_retorna_status_200_quando_um_objeto_valido_e_passado(){
        Address address = new Address();
        address.setEstado("ESTADO_TESTE");
        address.setRua("RUA_TESTE");
        address.setNumero("30");
        address.setCidade("CIDADE_TESTE");
        address.setCep("38300000");
        address.setBairro("BAIRRO_TESTE");
        UUID uuid = UUID.randomUUID();
        address.setIdAddress(uuid);

        Optional<Address> optional = Optional.empty();

        Mockito.doNothing().when(repository).deleteById(uuid);

        Mockito.when(repository.findById(uuid)).thenReturn(optional);

        ResponseEntity response = service.deletarAddress(uuid);

        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_deletarAddress_retorna_status_304_quando_nao_consegue_deletar_o_registro_no_banco_de_dados(){
        Address address = new Address();
        address.setEstado("ESTADO_TESTE");
        address.setRua("RUA_TESTE");
        address.setNumero("30");
        address.setCidade("CIDADE_TESTE");
        address.setCep("38300000");
        address.setBairro("BAIRRO_TESTE");
        UUID uuid = UUID.randomUUID();
        address.setIdAddress(uuid);

        Optional<Address> optional = Optional.of(address);

        Mockito.doNothing().when(repository).deleteById(uuid);

        Mockito.when(repository.findById(uuid)).thenReturn(optional);

        ResponseEntity response = service.deletarAddress(uuid);

        Assertions.assertEquals(304, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_deletarAddress_retorna_status_500_quando_a_conexao_com_o_banco_falha(){
        Address address = new Address();
        address.setEstado("ESTADO_TESTE");
        address.setRua("RUA_TESTE");
        address.setNumero("30");
        address.setCidade("CIDADE_TESTE");
        address.setCep("38300000");
        address.setBairro("BAIRRO_TESTE");
        UUID uuid = UUID.randomUUID();
        address.setIdAddress(uuid);


        Mockito.when(service.deletarAddress(uuid)).thenThrow(RuntimeException.class);


        ResponseEntity response = service.deletarAddress(uuid);

        Assertions.assertEquals(500, response.getStatusCode().value());
    }
}
