package com.sogeti.controller;

import java.io.IOException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sogeti.command.Command;

@RestController
public class DetailsController extends PrimaryController {

	private static final Logger logger = Logger.getLogger(DetailsController.class);

	/**
	 * Here we set the request url and type. This method is used to get all the
	 * DetailEntities from the repository using the message queue in the
	 * MessageAdapter
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public String getAllDetails() throws InterruptedException, IOException {
		logger.debug("In the /details endpoints");

		String response;
		command = new Command();
		command.setType(Command.queryType.GET);
		command.setTable(Command.queryTable.DETAILS);
		command.setQuantity(Command.queryQuantity.MULTIPLE);

		stringifiedCommand = gson.toJson(command);

		response = client.call(stringifiedCommand);
		return response;
	}

	/**
	 * Here we set the request url. This method takes the variable from the url path
	 * and uses it to get a DetailEntity from the repository using the message queue
	 * in the MessageAdapter
	 * 
	 * @throws IOException
	 */
	@GetMapping("/details/{details_id}")
	public String getDetailsById(@PathVariable("details_id") int detailsId) throws InterruptedException, IOException {
		logger.debug("In the /details endpoints");

		String response;
		values = new HashMap<>();
		values.put("id", String.valueOf(detailsId));
		
		command = new Command();
		command.setType(Command.queryType.GET);
		command.setTable(Command.queryTable.DETAILS);
		command.setQuantity(Command.queryQuantity.SINGLE);
		command.setValues(values);

		stringifiedCommand = gson.toJson(command);

		response = client.call(stringifiedCommand);
		return response;
	}
}
