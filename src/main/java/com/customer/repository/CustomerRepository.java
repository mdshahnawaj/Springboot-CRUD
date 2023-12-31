package com.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.customer.model.CustomerEntity;

/**
 * This interface is used to extend Jpa repository to perform crud operation and helps to define custom query.
 */
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {

	boolean existsByEmailAndIdNotOrMobileNumberAndIdNot(String email, int emailId, String mobileNumber, int mobileId);

}