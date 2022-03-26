package com.report.service.reportservice.consumer;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.report.service.reportservice.entity.User;
import com.report.service.reportservice.repository.UserRepository;

@Service
public class CustomerConsumer {
	private final Logger logger = LoggerFactory.getLogger(CustomerConsumer.class);

	@Autowired
	private UserRepository UserRepository;

	@KafkaListener(topics = "CUSTOMER_SAVE1")
	public void consume1(String message) throws IOException {
		logger.info(String.format("#### -> Consumed message -> %s", message));

		ObjectMapper mapper = new ObjectMapper();
		User user = mapper.readValue(message, User.class);

		logger.info("Ojbect" + user.getName());

		UserRepository.save(user);
	}
	
	@KafkaListener(topics = "CUSTOMER_SAVE2")
	public void consume2(String message) throws IOException {
		logger.info(String.format("#### -> Consumed message -> %s", message));

		ObjectMapper mapper = new ObjectMapper();
		User user = mapper.readValue(message, User.class);

		logger.info("Ojbect" + user.getName());

		UserRepository.save(user);
	}
}
