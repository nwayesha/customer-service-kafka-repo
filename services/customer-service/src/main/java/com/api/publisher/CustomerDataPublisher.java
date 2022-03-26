package com.api.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.api.entity.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Service
public class CustomerDataPublisher {
	private static final Logger logger = LoggerFactory.getLogger(CustomerDataPublisher.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void sendMessage(String topic, Customer customer) {
		try {
			logger.info(String.format("#### -> Producing message -> %s", customer.getName()));

			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = ow.writeValueAsString(customer);

			this.kafkaTemplate.send(topic, json);
		} catch (Exception e) {
			e.printStackTrace();

		}

	}
}
