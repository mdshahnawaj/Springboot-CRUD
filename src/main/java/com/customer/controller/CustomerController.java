package com.customer.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.customer.constant.MessageConstant;
import com.customer.dto.CustomerDto;
import com.customer.exception.EmailOrMobileAlreadyExists;
import com.customer.exception.ResourceNotFoundException;
import com.customer.model.CustomerEntity;
import com.customer.service.CustomerService;

/**
 * This is controller class is used for perform crud rest api operation
 * @author MdShahnawaj
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	/**
	 * This end-point used to save customer details with email and mobile validation
	 * @param customerDto
	 * @return response
	 */
	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody CustomerDto customerDto) {
		try {
			customerService.saveDetails(customerDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(MessageConstant.SAVE_SUCCESS_MESSAGE);
		} catch (EmailOrMobileAlreadyExists exception) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
		}
	}

	/**
	 * This end-point used to get all customer details
	 * @return customer
	 */
	@GetMapping
	public ResponseEntity<Object> read() {
		List<CustomerEntity> customerDetails = customerService.getAllCustomer();

		if (customerDetails.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageConstant.RECORD_NOT_FOUND_MESSAGE);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(customerDetails);
		}
	}

	/**
	 * This end-point used to get customer details by id
	 * @return customerDetails
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Object> getCustomerById(@PathVariable int id) {
		try {
			CustomerEntity customerDetails = customerService.getCustomerById(id);
			return ResponseEntity.status(HttpStatus.OK).body(customerDetails);
		} catch (ResourceNotFoundException exception) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
		}
	}

	/**
	 * This end-point used to update customer details
	 * @param customerDto
	 * @return response
	 */
	@PutMapping
	public ResponseEntity<Object> update(@Valid @RequestBody CustomerDto customerDto) {
		try {
			customerService.saveDetails(customerDto);
			return ResponseEntity.status(HttpStatus.OK).body(MessageConstant.UPDATE_SUCCESS_MESSAGE);
		} catch (EmailOrMobileAlreadyExists exception) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
		}
	}

	/**
	 * This end-point used to delete customer details by id
	 * @param id
	 * @return response message
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCustomerById(@PathVariable int id) {
		try {
			customerService.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body(MessageConstant.CUSTOMER_DELETED_MESSAGE + id);
		} catch (ResourceNotFoundException exception) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
		}
	}

	/**
	 * This end-point used to delete all customer details from database
	 * @return response message
	 */
	@DeleteMapping
	public ResponseEntity<String> deleteAllCustomers() {
		try {
			String responseMessage = customerService.deleteAllCustomer();
			return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
		} catch (RuntimeException exception) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
		}
	}

}