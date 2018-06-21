package controller.test.cases;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;

import com.sogeti.controller.DetailsController;

public class DetailsControllerTestCases {

	private DetailsController controller = new DetailsController();
	private String response;
	private final int TEST_ID = 100;

	@Test
	public void testGetAllDetails() throws InterruptedException, IOException {
		response = controller.getAllDetails();
		assertNotNull(response);
	}

	@Test
	public void testGetObjectById() throws InterruptedException, IOException {
		response = controller.getDetailsById(TEST_ID);
		assertNotNull(response);
	}

}
