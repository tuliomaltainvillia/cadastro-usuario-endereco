package com.invillia.cadastrousuarioendereco.repository;

import com.invillia.cadastrousuarioendereco.entity.Address;
import com.invillia.cadastrousuarioendereco.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
}
