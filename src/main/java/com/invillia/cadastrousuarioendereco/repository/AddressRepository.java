package com.invillia.cadastrousuarioendereco.repository;

import com.invillia.cadastrousuarioendereco.entity.Address;
import com.invillia.cadastrousuarioendereco.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {

    @Query("FROM Address a WHERE " +
            "(a.rua LIKE %:filtro%) OR " +
            "(a.numero LIKE %:filtro%) OR " +
            "(a.bairro LIKE %:filtro%) OR " +
            "(a.cidade LIKE %:filtro%) OR " +
            "(a.cep LIKE %:filtro%) OR " +
            "(a.estado LIKE %:filtro%)")
    Page<Address> buscarPaginado(@Param(value = "filtro") String filtro, Pageable pageable);
}
