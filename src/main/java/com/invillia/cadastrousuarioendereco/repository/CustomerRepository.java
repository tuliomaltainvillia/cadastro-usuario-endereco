package com.invillia.cadastrousuarioendereco.repository;

import com.invillia.cadastrousuarioendereco.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    @Query("FROM Customer c WHERE " +
            "(c.email LIKE %:filtro%) OR " +
            "(c.cpf_cnpj LIKE %:filtro%) OR " +
            "(c.tipo LIKE %:filtro%) OR " +
            "(c.telefone LIKE %:filtro%)")
    Page<Customer> buscarPaginado(@Param(value = "filtro") String filtro, Pageable pageable);
}
