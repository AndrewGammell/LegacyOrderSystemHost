package com.sogeti.adapter;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Envelope;
import com.sogeti.controller.OrderController;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;

public class MessageAdapter {

	private static final Logger logger = Logger.getLogger(OrderController.class);

	private Connection connection;
	private Channel channel;
	private String RPC_QUEUE_NAME = "rpc_queue";
	private String replyQueueName;
	private static MessageAdapter adapter;

	/**
	 * This method is used to get the instance of this class and create the instance
	 * on the first call only
	 */
	public static MessageAdapter getInstance() {
		if (adapter == null) {
			synchronized (MessageAdapter.class) {
				try {
					adapter = new MessageAdapter();
				} catch (IOException ioe) {
					logger.error(ioe);
				} catch (TimeoutException te) {
					logger.error(te);
				}
			}
		}
		return adapter;
	}

	/**
	 * The constructor is private to ensure that another instance of the class can
	 * not be created externally
	 */
	private MessageAdapter() throws IOException, TimeoutException {
		logger.debug("In Client constructor");

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");

		connection = factory.newConnection();
		channel = connection.createChannel();
	}

	/**
	 * This method is used to send the message, it sets the response queue
	 * properties and handles the response
	 */
	public String call(String message) throws IOException, InterruptedException {
		logger.debug("In call method from MessageAdapter");

		final String corrId = UUID.randomUUID().toString();
		replyQueueName = channel.queueDeclare().getQueue();

		AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName)
				.build();

		channel.basicPublish("", RPC_QUEUE_NAME, props, message.getBytes("UTF-8"));

		final BlockingQueue<String> response = new ArrayBlockingQueue<String>(1);

		// This is taking an anonymous inner class to handle the consumption of the
		// return message
		channel.basicConsume(replyQueueName, true, new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				logger.debug("In handleDelivery of the MessageAdapter");

				if (properties.getCorrelationId().equals(corrId)) {
					response.offer(new String(body, "UTF-8"));
				}
			}
		});

		return response.take();
	}

	// This method closes the connection
	public void close() throws IOException {
		connection.close();
	}

}
