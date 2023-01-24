package com.invillia.cadastrousuarioendereco.service;

import com.invillia.cadastrousuarioendereco.entity.Address;
import com.invillia.cadastrousuarioendereco.repository.AddressRepository;
import com.invillia.cadastrousuarioendereco.response.ViaCepResponse;
import com.invillia.cadastrousuarioendereco.utils.ValidarAddress;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AddressService {

    private AddressRepository addressRepository;
    private ViaCepService viaCepService;

    public AddressService(AddressRepository addressRepository, ViaCepService viaCepService) {
        this.addressRepository = addressRepository;
        this.viaCepService = viaCepService;
    }

    private void verificarSeEnderecoExiste(String cep) throws Exception{
        cep = cep.replaceAll("\\.", "").replaceAll("-", "");
        ViaCepResponse response = this.viaCepService.call(cep);

        if(null == response.getCep()){
            throw new Exception("Cep inexistente");
        }
    }

    public ResponseEntity salvarEndereco(Address address) {
        try {
            this.verificarSeEnderecoExiste(address.getCep());
            ValidarAddress validarAddress = new ValidarAddress(address);
            validarAddress.validar();
            UUID uuid = UUID.randomUUID();
            address.setIdAddress(uuid);
            return ResponseEntity.ok(addressRepository.save(address));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    public ResponseEntity buscarEndereco(Integer ultimoIndice,Integer totalPorPagina, String filtro) {
        try {
            Pageable pageable = PageRequest.of(ultimoIndice, totalPorPagina);
            List<Address> addressList = addressRepository.buscarPaginado(filtro, pageable).stream().toList();
            if (addressList.size() > 0) {
                return ResponseEntity.ok(addressList);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    public ResponseEntity buscarUnicoEndereco(UUID uuid) {
        try {
            Address address = addressRepository.findById(uuid).orElse(null);
            if (null == address) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(address);
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    public ResponseEntity editarAddress(UUID uuid, Address address) {
        try {
            Address address1 = addressRepository.save(address);
            return ResponseEntity.ok(address1);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }

    }

    public ResponseEntity deletarAddress(UUID uuid) {
        try {
            addressRepository.deleteById(uuid);
            Address address = addressRepository.findById(uuid).orElse(null);
            if (null == address) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(304).build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }
}
