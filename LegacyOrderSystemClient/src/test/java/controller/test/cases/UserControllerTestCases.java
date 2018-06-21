package controller.test.cases;


import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;

import com.sogeti.controller.UserController;

public class UserControllerTestCases {

	private UserController controller = new UserController();
	private String response;
	private final String TEST_EMAIL = "tomselleck@test.com";
	private final String TEST_PASSWORD = "selleck";
	
	
	@Test
	public void testGetUserWithCredentials() throws InterruptedException, IOException {
		
		response = controller.getUserWithCredentials(TEST_EMAIL, TEST_PASSWORD);
		assertNotNull(response);
	}
}
