package com.invillia.cadastrousuarioendereco.repository;

import com.invillia.cadastrousuarioendereco.entity.CustomerAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, UUID> {

    @Query("FROM CustomerAddress c WHERE " +
            "(CAST(c.idCustomer AS STRING) LIKE %:filtro%) OR " +
            "(CAST(c.idAddress AS STRING) LIKE %:filtro%) OR " +
            "(CAST(c.principal AS BOOLEAN) LIKE %:filtro%)")
    Page<CustomerAddress> buscarPaginado(@Param(value = "filtro") String filtro, Pageable pageable);
}
