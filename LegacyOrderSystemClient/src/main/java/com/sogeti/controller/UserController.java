package com.sogeti.controller;

import java.io.IOException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sogeti.command.Command;

@RestController
public class UserController extends PrimaryController {

	private static final Logger logger = Logger.getLogger(UserController.class);

	/**
	 * Here we set the request url and type. This method is used to get all the
	 * UserEntities from the repository using the message queue in the
	 * MessageAdapter
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String getUserWithCredentials(@RequestParam(value = "email", defaultValue = "null") String email,
			@RequestParam(value = "password", defaultValue = "null") String password)
			throws InterruptedException, IOException {
		logger.debug("In the /user endpoints");
		
		String response;
		values = new HashMap<>();
		values.put("email", email);
		values.put("password", password);
		
		command = new Command();
		command.setType(Command.queryType.GET);
		command.setTable(Command.queryTable.USERS);
		command.setQuantity(Command.queryQuantity.SINGLE);
		command.setValues(values);
		
		stringifiedCommand = gson.toJson(command);

		response = client.call(stringifiedCommand);
		return response;
	}

//	/**
//	 * Here we set the request url. This method takes the variable from the url path
//	 * and uses it to get a DetailEntity from the repository using the message queue
//	 * in the MessageAdapter
//	 * 
//	 * @throws IOException
//	 */
//	@RequestMapping(value = "/users", method = RequestMethod.GET)
//	public String getAllUsers() throws InterruptedException, IOException {
//		logger.debug("in the /users endpoint");
//		String response;
//
//		response = client.call(GET_ALL_USERS);
//		return response;
//	}
}
