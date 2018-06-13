package com.sogeti.controller;

import java.io.IOException;

import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sogeti.adapter.MessageAdapter;

@RestController
public class DetailsController extends PrimaryController {

	private static final Logger logger = Logger.getLogger(DetailsController.class);
	private MessageAdapter client;
	private final String GET_ALL_DETAILS = "getDetails";
	private final String GET_DETAILS_BY_ID = "getDetailById/%d";

	// The MessageAdapter instance is retrieved
	public DetailsController() {
		client = MessageAdapter.getInstance();
	}

	/**
	 * Here we set the request url and type. This method is used to get all the
	 * DetailEntities from the repository using the message queue in the
	 * MessageAdapter
	 */
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public String getAllDetails() throws InterruptedException {
		logger.debug("In the /details endpoints");
		String response;

		try {
			response = client.call(GET_ALL_DETAILS);

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
	@GetMapping("/details/{details_id}")
	public String getDetailsById(@PathVariable("details_id") int detailsId) throws InterruptedException {
		logger.debug("In the /details endpoints");
		String response;

		try {
			response = client.call(String.format(GET_DETAILS_BY_ID, detailsId));

		} catch (IOException ioe) {
			response = "IOException occurred";
			logger.error(ioe);
		}
		return response;
	}
}
