package suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import controller.test.cases.DetailsControllerTestCases;
import controller.test.cases.OrderControllerTestCases;
import controller.test.cases.UserControllerTestCases;

@RunWith(Suite.class)
@Suite.SuiteClasses({DetailsControllerTestCases.class, OrderControllerTestCases.class, UserControllerTestCases.class})
public class TestSuite {

}
