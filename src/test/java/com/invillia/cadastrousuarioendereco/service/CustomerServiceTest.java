package com.invillia.cadastrousuarioendereco.service;

import com.invillia.cadastrousuarioendereco.entity.Address;
import com.invillia.cadastrousuarioendereco.entity.Customer;
import com.invillia.cadastrousuarioendereco.entity.CustomerAddress;
import com.invillia.cadastrousuarioendereco.repository.CustomerRepository;
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
public class CustomerServiceTest {

    @Mock
    private CustomerRepository repository;

    @Mock
    private CustomerAddressService customerAddressService;

    @Mock
    private AddressService addressService;

    @InjectMocks
    private CustomerService service;

    @Test
    public void teste_se_o_metodo_salvar_retorna_status_200_quando_um_objeto_valido_e_passado(){
        Customer customer = new Customer();
        customer.setCpf_cnpj("11166644455");
        customer.setEmail("teste@teste.com");
        customer.setTelefone("3455558888");
        customer.setTipo("PF");

        Address address = new Address();
        address.setEstado("ESTADO_TESTE");
        address.setRua("RUA_TESTE");
        address.setNumero("30");
        address.setCidade("CIDADE_TESTE");
        address.setCep("38300000");
        address.setBairro("BAIRRO_TESTE");

        UUID uuid = UUID.randomUUID();
        address.setIdAddress(uuid);

        Mockito.when(addressService.buscarUnicoEndereco(uuid)).thenReturn(ResponseEntity.ok(address));

        Mockito.when(repository.save(customer)).thenReturn(customer);

        CustomerAddress customerAddress = new CustomerAddress();

        Mockito.when(customerAddressService.salvar(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(ResponseEntity.ok(customerAddress));

        ResponseEntity response = service.salvar(customer,uuid);

        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_salvar_retorna_status_406_quando_um_endereco_nao_existe(){
        Customer customer = new Customer();
        customer.setCpf_cnpj("11166644455");
        customer.setEmail("teste@teste.com");
        customer.setTelefone("3455558888");
        customer.setTipo("PF");

        UUID uuid = UUID.randomUUID();

        Mockito.when(addressService.buscarUnicoEndereco(uuid)).thenReturn(ResponseEntity.noContent().build());

        ResponseEntity response = service.salvar(customer,uuid);

        Assertions.assertEquals(406, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_salvar_retorna_status_500_quando_a_conexao_com_o_banco_de_dados_falha(){
        Customer customer = new Customer();
        customer.setCpf_cnpj("11166644455");
        customer.setEmail("teste@teste.com");
        customer.setTelefone("3455558888");
        customer.setTipo("PF");

        UUID uuid = UUID.randomUUID();

        Mockito.when(addressService.buscarUnicoEndereco(uuid)).thenThrow(RuntimeException.class);

        ResponseEntity response = service.salvar(customer,uuid);

        Assertions.assertEquals(500, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_salvar_retorna_um_objeto_valido_quando_um_objeto_valido_e_passado(){
        Customer customer = new Customer();
        customer.setCpf_cnpj("11166644455");
        customer.setEmail("teste@teste.com");
        customer.setTelefone("3455558888");
        customer.setTipo("PF");

        Address address = new Address();
        address.setEstado("ESTADO_TESTE");
        address.setRua("RUA_TESTE");
        address.setNumero("30");
        address.setCidade("CIDADE_TESTE");
        address.setCep("38300000");
        address.setBairro("BAIRRO_TESTE");

        UUID uuid = UUID.randomUUID();
        address.setIdAddress(uuid);

        ResponseEntity responseAddress = ResponseEntity.ok(address);
        Mockito.when(addressService.buscarUnicoEndereco(uuid)).thenReturn(responseAddress);

        Mockito.when(repository.save(customer)).thenReturn(customer);

        Customer response = (Customer) service.salvar(customer,uuid).getBody();

        Assertions.assertEquals("teste@teste.com", response.getEmail());
        Assertions.assertEquals("11166644455", response.getCpf_cnpj());
        Assertions.assertEquals("3455558888", response.getTelefone());
        Assertions.assertEquals("PF", response.getTipo());
        Assertions.assertNotNull(response.getIdCustomer());
    }

    @Test
    public void teste_se_o_metodo_buscar_retorna_status_200_quando_um_objeto_valido_e_passado(){
        List<Customer> list = new ArrayList<>();
        list.addAll(List.of(new Customer(), new Customer(), new Customer()));

        Pageable pageable = PageRequest.of(0,10);

        Page<Customer> page = new PageImpl<>(list, pageable, list.size());

        Mockito.when(repository.buscarPaginado("",pageable)).thenReturn(page);

        ResponseEntity response = service.buscar(0,10,"");

        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_buscar_retorna_uma_lista_valida_quando_um_objeto_valido_e_passado(){
        List<Customer> list = new ArrayList<>();
        list.addAll(List.of(new Customer(), new Customer(), new Customer()));

        Pageable pageable = PageRequest.of(0,10);

        Page<Customer> page = new PageImpl<>(list, pageable, list.size());

        Mockito.when(repository.buscarPaginado("",pageable)).thenReturn(page);

        List<Customer> response = (List<Customer>) service.buscar(0,10,"").getBody();

        Assertions.assertEquals(3, response.size());
    }

    @Test
    public void teste_se_o_metodo_buscar_retorna_status_204_quando_um_objeto_valido_e_passado(){
        List<Customer> list = new ArrayList<>();
        list.addAll(List.of(new Customer(), new Customer(), new Customer()));

        Pageable pageable = PageRequest.of(0,10);

        Page<Customer> page = Page.empty();

        Mockito.when(repository.buscarPaginado("",pageable)).thenReturn(page);

        ResponseEntity response = service.buscar(0,10,"");

        Assertions.assertEquals(204, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_buscar_retorna_status_500_quando_a_conexao_com_o_banco_de_dados_falha(){
        List<Customer> list = new ArrayList<>();
        list.addAll(List.of(new Customer(), new Customer(), new Customer()));

        Pageable pageable = PageRequest.of(0,10);

        Page<Customer> page = Page.empty();

        Mockito.when(repository.buscarPaginado("", pageable)).thenThrow(RuntimeException.class);
        ResponseEntity response = service.buscar(0,10,"");

        Assertions.assertEquals(500, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_buscarUnico_retorna_status_200_quando_um_objeto_valido_e_passado(){
        Customer customer = new Customer();
        customer.setCpf_cnpj("11166644455");
        customer.setEmail("teste@teste.com");
        customer.setTelefone("3455558888");
        customer.setTipo("PF");

        UUID uuid = UUID.randomUUID();

        Optional<Customer> optional = Optional.of(customer);

        Mockito.when(repository.findById(uuid)).thenReturn(optional);

        ResponseEntity response = service.buscarUnico(uuid);

        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_buscarUnico_retorna_status_204_quando_nenhum_registro_e_encontrado(){
        Customer customer = new Customer();
        customer.setCpf_cnpj("11166644455");
        customer.setEmail("teste@teste.com");
        customer.setTelefone("3455558888");
        customer.setTipo("PF");

        UUID uuid = UUID.randomUUID();

        Optional<Customer> optional = Optional.empty();

        Mockito.when(repository.findById(uuid)).thenReturn(optional);

        ResponseEntity response = service.buscarUnico(uuid);

        Assertions.assertEquals(204, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_buscarUnico_retorna_status_500_quando_a_conexao_com_o_banco_de_dados_falha(){
        UUID uuid = UUID.randomUUID();


        Mockito.when(repository.findById(uuid)).thenThrow(RuntimeException.class);

        ResponseEntity response = service.buscarUnico(uuid);

        Assertions.assertEquals(500, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_editarCustomer_retorna_status_200_quando_um_objeto_valido_e_passado(){
        Customer customer = new Customer();
        customer.setCpf_cnpj("11166644455");
        customer.setEmail("teste@teste.com");
        customer.setTelefone("3455558888");
        customer.setTipo("PF");

        UUID uuid = UUID.randomUUID();

        Mockito.when(repository.save(customer)).thenReturn(customer);

        ResponseEntity response = service.editarCustomer(uuid,customer);

        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_editarCustomer_retorna_status_500_quando_a_conexao_com_o_banco_de_dados_falha(){
        Customer customer = new Customer();
        customer.setCpf_cnpj("11166644455");
        customer.setEmail("teste@teste.com");
        customer.setTelefone("3455558888");
        customer.setTipo("PF");

        UUID uuid = UUID.randomUUID();

        Mockito.when(repository.save(customer)).thenThrow(RuntimeException.class);

        ResponseEntity response = service.editarCustomer(uuid,customer);

        Assertions.assertEquals(500, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_deletarCustomer_retorna_status_200_quando_um_objeto_valido_e_passado(){
        UUID uuid = UUID.randomUUID();

        Customer customer = new Customer();
        customer.setCpf_cnpj("11166644455");
        customer.setEmail("teste@teste.com");
        customer.setTelefone("3455558888");
        customer.setTipo("PF");
        customer.setIdCustomer(uuid);

        Mockito.doNothing().when(repository).deleteById(uuid);

        Optional<Customer> optional = Optional.empty();
        Mockito.when(repository.findById(uuid)).thenReturn(optional);

        ResponseEntity response = service.deletarCustomer(uuid);

        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_deletarCustomer_retorna_status_304_quando_um_a_delecao_nao_funciona(){
        UUID uuid = UUID.randomUUID();

        Customer customer = new Customer();
        customer.setCpf_cnpj("11166644455");
        customer.setEmail("teste@teste.com");
        customer.setTelefone("3455558888");
        customer.setTipo("PF");
        customer.setIdCustomer(uuid);

        Mockito.doNothing().when(repository).deleteById(uuid);

        Optional<Customer> optional = Optional.of(customer);

        Mockito.when(repository.findById(uuid)).thenReturn(optional);

        ResponseEntity response = service.deletarCustomer(uuid);

        Assertions.assertEquals(304, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_deletarCustomer_retorna_status_500_quando_a_conexao_com_o_banco_de_dados_falha(){
        UUID uuid = UUID.randomUUID();

        Customer customer = new Customer();
        customer.setCpf_cnpj("11166644455");
        customer.setEmail("teste@teste.com");
        customer.setTelefone("3455558888");
        customer.setTipo("PF");
        customer.setIdCustomer(uuid);

        Mockito.doNothing().when(repository).deleteById(uuid);


        Mockito.when(repository.findById(uuid)).thenThrow(RuntimeException.class);

        ResponseEntity response = service.deletarCustomer(uuid);

        Assertions.assertEquals(500, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_vincularEndereco_retorna_status_200_quando_consegue_vincular_endereco(){
        UUID idCustomer = UUID.randomUUID();
        UUID idAddress = UUID.randomUUID();

        Address address = new Address();

        List<CustomerAddress> customerAddressList = new ArrayList<>();

        CustomerAddress c1 = new CustomerAddress();
        c1.setIdCustomerAddress(UUID.randomUUID());
        c1.setIdCustomer(idCustomer);
        c1.setIdAddress(UUID.randomUUID());
        c1.setPrincipal(true);

        CustomerAddress c2 = new CustomerAddress();
        c2.setIdCustomerAddress(UUID.randomUUID());
        c2.setIdCustomer(idCustomer);
        c2.setIdAddress(UUID.randomUUID());
        c2.setPrincipal(false);

        customerAddressList.addAll(List.of(c1,c2));

        CustomerAddress customerAddress = new CustomerAddress();

        Mockito.when(addressService.buscarUnicoEndereco(idAddress)).thenReturn(ResponseEntity.ok(address));
        Mockito.when(customerAddressService.findAll()).thenReturn(ResponseEntity.ok(customerAddressList));
        Mockito.when(customerAddressService.salvar(idCustomer, idAddress, false)).thenReturn(ResponseEntity.ok(customerAddress));

        ResponseEntity response = service.vincularEndereco(idCustomer, idAddress);

        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_vincularEndereco_retorna_status_401_quando_o_usuario_nao_pode_mais_vincular_enderecos(){
        UUID idCustomer = UUID.randomUUID();
        UUID idAddress = UUID.randomUUID();

        Address address = new Address();

        List<CustomerAddress> customerAddressList = new ArrayList<>();

        CustomerAddress c1 = new CustomerAddress();
        c1.setIdCustomerAddress(UUID.randomUUID());
        c1.setIdCustomer(idCustomer);
        c1.setIdAddress(UUID.randomUUID());
        c1.setPrincipal(true);

        CustomerAddress c2 = new CustomerAddress();
        c2.setIdCustomerAddress(UUID.randomUUID());
        c2.setIdCustomer(idCustomer);
        c2.setIdAddress(UUID.randomUUID());
        c2.setPrincipal(false);

        CustomerAddress c3 = new CustomerAddress();
        c3.setIdCustomerAddress(UUID.randomUUID());
        c3.setIdCustomer(idCustomer);
        c3.setIdAddress(UUID.randomUUID());
        c3.setPrincipal(false);

        CustomerAddress c4 = new CustomerAddress();
        c4.setIdCustomerAddress(UUID.randomUUID());
        c4.setIdCustomer(idCustomer);
        c4.setIdAddress(UUID.randomUUID());
        c4.setPrincipal(false);

        CustomerAddress c5 = new CustomerAddress();
        c5.setIdCustomerAddress(UUID.randomUUID());
        c5.setIdCustomer(idCustomer);
        c5.setIdAddress(UUID.randomUUID());
        c5.setPrincipal(false);

        customerAddressList.addAll(List.of(c1,c2,c3,c4,c5));


        Mockito.when(addressService.buscarUnicoEndereco(idAddress)).thenReturn(ResponseEntity.ok(address));
        Mockito.when(customerAddressService.findAll()).thenReturn(ResponseEntity.ok(customerAddressList));

        ResponseEntity response = service.vincularEndereco(idCustomer, idAddress);

        Assertions.assertEquals(401, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_vincularEndereco_retorna_status_406_quando_o_endereco_passado_nao_existe(){
        UUID idCustomer = UUID.randomUUID();
        UUID idAddress = UUID.randomUUID();

        Mockito.when(addressService.buscarUnicoEndereco(idAddress)).thenReturn(ResponseEntity.noContent().build());

        ResponseEntity response = service.vincularEndereco(idCustomer, idAddress);

        Assertions.assertEquals(406, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_vincularEndereco_retorna_status_500_quando_a_conexao_com_o_banco_de_dados_falha(){
        UUID idCustomer = UUID.randomUUID();
        UUID idAddress = UUID.randomUUID();

        Mockito.when(addressService.buscarUnicoEndereco(idAddress)).thenThrow(RuntimeException.class);

        ResponseEntity response = service.vincularEndereco(idCustomer, idAddress);

        Assertions.assertEquals(500, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_tornarPrincipal_retorna_status_200_quando_altera_endereco_principal(){
        UUID idCustomer = UUID.randomUUID();
        UUID idAddress = UUID.randomUUID();

        List<CustomerAddress> customerAddressList = new ArrayList<>();

        CustomerAddress c1 = new CustomerAddress();
        c1.setIdCustomerAddress(UUID.randomUUID());
        c1.setIdCustomer(idCustomer);
        c1.setIdAddress(UUID.randomUUID());
        c1.setPrincipal(true);

        CustomerAddress c2 = new CustomerAddress();
        c2.setIdCustomerAddress(UUID.randomUUID());
        c2.setIdCustomer(idCustomer);
        c2.setIdAddress(idAddress);
        c2.setPrincipal(false);

        customerAddressList.addAll(List.of(c1,c2));

        Mockito.when(customerAddressService.findAll()).thenReturn(ResponseEntity.ok(customerAddressList));

        List<CustomerAddress> list = List.copyOf(customerAddressList);

        list.get(0).setPrincipal(false);
        list.get(1).setPrincipal(true);

        Mockito.when(customerAddressService.inserirVarios(customerAddressList)).thenReturn(ResponseEntity.ok(list));

        ResponseEntity response = service.tornarPrincipal(idCustomer,idAddress);

        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_tornarPrincipal_retorna_status_304_quando_nao_existem_registros_no_banco(){
        UUID idCustomer = UUID.randomUUID();
        UUID idAddress = UUID.randomUUID();

        List<CustomerAddress> customerAddressList = new ArrayList<>();

        Mockito.when(customerAddressService.findAll()).thenReturn(ResponseEntity.ok(customerAddressList));


        ResponseEntity response = service.tornarPrincipal(idCustomer,idAddress);

        Assertions.assertEquals(304, response.getStatusCode().value());
    }

    @Test
    public void teste_se_o_metodo_tornarPrincipal_retorna_status_500_quando_a_conexao_com_o_banco_de_dados_falha(){
        UUID idCustomer = UUID.randomUUID();
        UUID idAddress = UUID.randomUUID();

        Mockito.when(customerAddressService.findAll()).thenThrow(RuntimeException.class);


        ResponseEntity response = service.tornarPrincipal(idCustomer,idAddress);

        Assertions.assertEquals(500, response.getStatusCode().value());
    }
}
