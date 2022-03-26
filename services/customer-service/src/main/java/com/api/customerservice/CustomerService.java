package com.api.customerservice;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.entity.Customer;
import com.api.publisher.CustomerDataPublisher;
import com.api.repository.CustomerRepository;

@Service
public class CustomerService {
	final static Logger logger = LoggerFactory.getLogger(CustomerService.class);

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerDataPublisher customerDataPublisher;

	/**
	 * 
	 * @return
	 */
	public List<Customer> getCustomerDetails() {
		List<Customer> customerList = customerRepository.findAll();
		if (!customerList.isEmpty()) {
			logger.info("getting all customer details --");
			return customerList;
		}
		return null;
	}

	/**
	 * 
	 * @param customer_id
	 * @return
	 */
	public Customer getCustomerDetailsbyId(Integer customer_id) {
		Optional<Customer> customer = customerRepository.findById(customer_id);
		// return customer.isEmpty() ? null : customer.get();
		logger.info("getting customer details by id --");
		return customer.isPresent() ? customer.get() : null;

	}

	/**
	 * 
	 * @param customer
	 * @return
	 */
	public Customer updateCustomerDetails(Customer customer) {
		customerRepository.save(customer);
		return customer;
	}

	/**
	 * 
	 * @param customer
	 * @return
	 */
	public Customer saveCustomerDetails(Customer customer) {

		customerRepository.save(customer);
		logger.info("saving the customer" + customer.getId());

		// call Publisher service to message into Kafka
		customerDataPublisher.sendMessage("CUSTOMER_SAVE1", customer);
		return customer;
	}

	/**
	 * 
	 * @param customer
	 */
	public void deleteCustomerDetails(Customer customer) {
		logger.info("deleting the customer" + customer.getId());
		customerRepository.delete(customer);
	}

	public Customer getCustomerDetailsbyEmail(String email) {
		Optional<Customer> customer = customerRepository.findByEmail(email);
		// return customer.isEmpty() ? null : customer.get();
		logger.info("getting customer details by email --");
		return customer.isPresent() ? customer.get() : null;
	}
}
