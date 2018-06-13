package com.sogeti.controller;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sogeti.adapter.MessageAdapter;

@RestController
public class OrderController extends PrimaryController {
	private static final Logger logger = Logger.getLogger(OrderController.class);

	private MessageAdapter client;
	private final String GET_ALL_ORDERS = "getOrders";
	private final String GET_ORDER_BY_ID = "getOrderById/%s";

	// The MessageAdapter instance is retrieved
	public OrderController() {
		client = MessageAdapter.getInstance();
	}

	/**
	 * Here we set the request url and type. This method is used to get all the
	 * OrderEntities from the repository using the message queue in the
	 * MessageAdapter
	 */
	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	public String getAllOrders() throws InterruptedException {
		logger.debug("In the /orders endpoints");
		String response;

		try {
			response = client.call(GET_ALL_ORDERS);

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
	@GetMapping("/orders/{order_id}")
	public String getOrdersById(@PathVariable("order_id") int orderId) throws InterruptedException {
		logger.debug("In the /order/orderId endpoints");
		String response;

		try {
			response = client.call(String.format(GET_ORDER_BY_ID, orderId));

		} catch (IOException ioe) {
			response = "IOException occurred";
			logger.error(ioe);
		}
		return response;
	}
}
