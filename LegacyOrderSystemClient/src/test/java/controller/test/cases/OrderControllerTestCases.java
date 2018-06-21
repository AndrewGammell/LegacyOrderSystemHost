package controller.test.cases;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;

import com.sogeti.controller.OrderController;

public class OrderControllerTestCases {

	private OrderController controller = new OrderController();
	private String response;
	private final int TEST_ID = 100;

	@Test
	public void testGetAllDetails() throws InterruptedException, IOException {
		response = controller.getAllOrders();
		assertNotNull(response);
	}

	@Test
	public void testGetObjectById() throws InterruptedException, IOException {
		response = controller.getOrdersById(TEST_ID);
		assertNotNull(response);
	}

}
