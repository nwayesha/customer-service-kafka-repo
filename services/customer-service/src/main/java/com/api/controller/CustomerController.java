package com.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.customerservice.CustomerService;
import com.api.entity.Customer;

import io.swagger.annotations.ApiOperation;
import net.minidev.json.JSONObject;

@RestController
@RequestMapping("customer")
public class CustomerController {

	final static Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private CustomerService customerService;

	@ApiOperation(value = "Search all customers")
	@GetMapping("/customerDetails")
	public @ResponseBody ResponseEntity<List<Customer>> getCustomerDetails() {
		List<Customer> customerList = null;
		try {
			customerList = customerService.getCustomerDetails();			
		} catch (Exception e) {
			logger.error("exception in getting all customer details -- ",e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		return ResponseEntity.ok().body(customerList);
	}

	@ApiOperation(value = "Search Customer from id")
	@GetMapping("/find/id/{customer_id}")
	public @ResponseBody ResponseEntity<Customer> getCustomerDetailsbyId(@PathVariable Integer customerId) {
		Customer customer = null;
		try {
			logger.info("finding customer details by id" + customerId);
			customer = customerService.getCustomerDetailsbyId(customerId);	
			if(customer == null) {
				return ResponseEntity.badRequest().build();
			}
		} catch (Exception e) {
			logger.error("exception in getting all customer details -- ",e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		return ResponseEntity.ok().body(customer);		
		
	}
	
	@ApiOperation(value = "Search Customer by email")
	@GetMapping("/find/email/{email}")
	public @ResponseBody ResponseEntity<Customer> getCustomerDetailsbyEmail(@PathVariable String email) {
		Customer customer = null;
		try {
			logger.info("finding customer details by id" + email);
			customer = customerService.getCustomerDetailsbyEmail(email);	
			if(customer == null) {
				return ResponseEntity.badRequest().build();
			}
		} catch (Exception e) {
			logger.error("exception in getting all customer details -- ",e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		return ResponseEntity.ok().body(customer);		
		
	}
	
	@ApiOperation(value = "Create New Customer")
	@RequestMapping(value = "/Save", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> saveCustomerDetails(@RequestBody Customer customer, HttpServletResponse response) {
		try {
			customerService.saveCustomerDetails(customer);
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		return ResponseEntity.ok("Success");				
	}
	
	@ApiOperation(value = "Update Customer")
	@RequestMapping(value = "/update", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> updateCustomerDetails(@RequestBody Customer customer, HttpServletResponse response) {
		try {
			customer = customerService.updateCustomerDetails(customer);
			response.setStatus(HttpServletResponse.SC_CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		return ResponseEntity.ok(customer);	
	}	
	
	@ApiOperation(value = "Delete Customer")
	@RequestMapping(value = "/Delete", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> deleteCustomerDetails(@RequestBody Customer customer, HttpServletResponse response) {
		try {
			customerService.deleteCustomerDetails(customer);;
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		return ResponseEntity.ok("Success");				
	}	

	@GetMapping("/serviceCheck")
	@ResponseBody
	public String serviceCheck(HttpServletResponse response) {
		JSONObject json = new JSONObject();
		json.appendField("status", "success");
		ResponseEntity.ok().build(); 
		
		return json.toJSONString();
	}
}
