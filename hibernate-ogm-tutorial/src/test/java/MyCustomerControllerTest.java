import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import zentino.controllers.MyCustomerController;
import zentino.models.MyCustomer;

public class MyCustomerControllerTest {
    @Before
    public void setUp() throws Exception {
        MyCustomerController custController = new MyCustomerController();
        custController.deleteAllCustomer();
        custController.createCustomers();
    }

    @After
    public void tearDown() throws Exception {
        MyCustomerController custController = new MyCustomerController();
        custController.deleteAllCustomer();
    }

    @Test(expected = NullPointerException.class)
    public void testF_deleteCustomerTest() {
        MyCustomerController custController = new MyCustomerController();

        MyCustomer myCustomer = custController.getCustomer(1L);
        custController.deleteCustomer(1L);

        myCustomer = custController.getCustomer(1L);

        myCustomer.getC_ID();

        //fail("NullPointerException");
    }


    @Test
    public void testG_deleteAllCustomerTest() {
        MyCustomerController custController = new MyCustomerController();
        custController.deleteAllCustomer();
    }

}
