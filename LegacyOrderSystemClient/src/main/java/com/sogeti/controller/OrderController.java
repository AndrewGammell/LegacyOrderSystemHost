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
public class OrderController extends PrimaryController {
	private static final Logger logger = Logger.getLogger(OrderController.class);

	/**
	 * Here we set the request url and type. This method is used to get all the
	 * OrderEntities from the repository using the message queue in the
	 * MessageAdapter
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	public String getAllOrders() throws InterruptedException, IOException {
		logger.info("In the /orders endpoints");

		String response;
		command = new Command();
		command.setType(Command.queryType.GET);
		command.setQuantity(Command.queryQuantity.MULTIPLE);
		command.setTable(Command.queryTable.ORDERS);

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
	@GetMapping("/orders/{order_id}")
	public String getOrdersById(@PathVariable("order_id") int orderId) throws InterruptedException, IOException {
		logger.debug("In the /order/orderId endpoints");
		
		String response;
		values = new HashMap<>();
		values.put("id", String.valueOf(orderId));
		
		command = new Command();
		command.setType(Command.queryType.GET);
		command.setTable(Command.queryTable.ORDERS);
		command.setQuantity(Command.queryQuantity.SINGLE);
		command.setValues(values);

		stringifiedCommand = gson.toJson(command);

		response = client.call(stringifiedCommand);
		return response;
	}
}
