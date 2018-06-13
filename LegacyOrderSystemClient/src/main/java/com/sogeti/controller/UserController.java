package com.sogeti.controller;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sogeti.adapter.MessageAdapter;

@RestController
public class UserController extends PrimaryController {

	private static final Logger logger = Logger.getLogger(UserController.class);
	private MessageAdapter client;

	private final String GET_USER = "getUserWithCredentials/%s/%s";
	private final String GET_ALL_USERS = "getUsers";

	// The MessageAdapter instance is retrieved
	public UserController() {
		client = MessageAdapter.getInstance();
	}

	/**
	 * Here we set the request url and type. This method is used to get all the
	 * UserEntities from the repository using the message queue in the
	 * MessageAdapter
	 */
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String getUserWithCredentials(@RequestParam(value = "email", defaultValue = "null") String email,
			@RequestParam(value = "password", defaultValue = "null") String password) throws InterruptedException {
		logger.debug("In the /user endpoints");
		String response;

		try {
			response = client.call(String.format(GET_USER, email, password));

		} catch (IOException ioe) {
			response = "IOException occurred";
			logger.error(ioe);
		}

		return response;
	}

	/**
	 * Here we set the request url. This method takes the variable from the url path
	 * and uses it to get a DetailEntity from the repository using the message queue
	 * in the MessageAdapter
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String getAllUsers() throws InterruptedException {
		logger.debug("in the /users endpoint");
		String response;

		try {
			response = client.call(GET_ALL_USERS);

		} catch (IOException ioe) {
			response = "IOException occurred";
			logger.error(ioe);
		}

		return response;
	}
}
