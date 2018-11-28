import de.hsma.jens.controllers.CustomerController;
import de.hsma.jens.models.Customer;
import de.hsma.jens.tools.Config;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by jenskohler on 12.12.17.
 */


//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerControllerTest {


    @Before
    public void setUp() throws Exception {

        CustomerController custController = new CustomerController();

        custController.deleteAllCustomer();
        custController.createCustomers();


    }

    @After
    public void tearDown() throws Exception {

        CustomerController custController = new CustomerController();

        custController.deleteAllCustomer();


    }


    @Test
    public void testB_readCustomerTest() {
        CustomerController custController = new CustomerController();

        Customer cTest = custController.getCustomer(1);

        assertEquals(1, cTest.getC_ID(), 0.0001);


    }


    @Test
    public void testC_getAllCustomerTest() {
        CustomerController custController = new CustomerController();

        ArrayList<Customer> cList = (ArrayList<Customer>) custController.getAllCustomer();


        assertEquals(Config.NUMBER_OF_CUSTOMERS, cList.size(), 0.0001);


    }

    @Ignore
    @Test
    public void testC_getAllCustomerAsListTest() {
        CustomerController custController = new CustomerController();


        Integer numberOfCustomers = custController.getAllCustomerAsList();


        assertEquals(Config.NUMBER_OF_CUSTOMERS, numberOfCustomers, 0.0001);

    }


    @Test
    public void testD_updateCustomerTest() {
        CustomerController custController = new CustomerController();
        Customer custTest = null;

        List<Integer> cIDs = custController.getAllCustomerIDs();
        List<Customer> customersToUpdate = new ArrayList<Customer>();

        for (Integer id : cIDs) {
            //find a customer to update
            //Customer cTest = custController.getCustomer(id);
            Customer cTest = new Customer();

            cTest.setC_ADDR_ID(1);
            cTest.setC_BALANCE(99.9);
            cTest.setC_BIRTHDATE(new Date());
            cTest.setC_DATA("data_1");
            cTest.setC_DISCOUNT(99.9);
            cTest.setC_EMAIL("email_1");
            cTest.setC_EXPIRATION(new Date());
            cTest.setC_FNAME("fname_1");
            cTest.setC_ID(id);
            cTest.setC_LAST_LOGIN(new Date());
            cTest.setC_LOGIN(new Date());
            cTest.setC_PASSWD("password_1");
            cTest.setC_LNAME("lname_1");
            cTest.setC_PHONE("phone_1");
            cTest.setC_SINCE(new Date());
            cTest.setC_YTD_PMT(99.9);
            cTest.setC_UNAME("uname_1");

            customersToUpdate.add(cTest);
        }

        custController.updateCustomer(customersToUpdate);


        //retrieve an updated customer from the datastore
        custTest = custController.getCustomer(1);

        assertEquals(99.9, custTest.getC_YTD_PMT(), 0.0001);


    }

    @Ignore
    @Test
    public void testE_updateCustomersFromFileTest() {
        CustomerController custController = new CustomerController();

        custController.updateCustomersFromFile();

        List idList = custController.getAllCustomerIDs();


        assertEquals(Config.NUMBER_OF_CUSTOMERS, idList.size(), 0.0001);

    }


    @Test(expected = NullPointerException.class)
    public void testF_deleteCustomerTest() {
        CustomerController custController = new CustomerController();


        Customer cTest = custController.getCustomer(1);

        custController.deleteCustomer(1);

        cTest = custController.getCustomer(1);

        cTest.getC_ID();

        //fail("NullPointerException");

    }


    @Test
    public void testG_deleteAllCustomerTest() {
        CustomerController custController = new CustomerController();

        custController.deleteAllCustomer();

    }


}
