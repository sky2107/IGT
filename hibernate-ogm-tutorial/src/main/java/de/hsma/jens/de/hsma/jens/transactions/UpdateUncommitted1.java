package de.hsma.jens.de.hsma.jens.transactions;

import de.hsma.jens.controllers.CustomerController;
import de.hsma.jens.models.Customer;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.*;
import java.util.Date;

/**
 * Created by jenskohler on 06.12.17.
 */
public class UpdateUncommitted1 {
    //accessing JBoss's Transaction can be done differently but this one works nicely
    static TransactionManager tm = com.arjuna.ats.jta.TransactionManager.transactionManager();

    //build the EntityManagerFactory as you would build in in Hibernate ORM
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory(
            "ogm-postgresql");


    private static Logger logger = Logger.getRootLogger();


    public static void main(String[] args) {

        Customer customer1 = new Customer();

        customer1.setC_ADDR_ID(1);
        customer1.setC_BALANCE(11.11);
        customer1.setC_BIRTHDATE(new Date());
        customer1.setC_DATA("data_11");
        customer1.setC_DISCOUNT(11.11);
        customer1.setC_EMAIL("email_11");
        customer1.setC_EXPIRATION(new Date());
        customer1.setC_FNAME("fname_11");
        customer1.setC_ID(1);
        customer1.setC_LAST_LOGIN(new Date());
        customer1.setC_LOGIN(new Date());
        customer1.setC_PASSWD("password_11");
        customer1.setC_LNAME("lname_11");
        customer1.setC_PHONE("phone_11");
        customer1.setC_SINCE(new Date());
        customer1.setC_YTD_PMT(11.11);
        customer1.setC_UNAME("uname_11");


        CustomerController customerController = new CustomerController();
        customerController.createCustomers();


        try {
            tm.begin();

            logger.info("TA begins");
            EntityManager em = emf.createEntityManager();


            Customer customerToUpdate = em.find(Customer.class, 1);
            logger.info("Found customer: " + customerToUpdate.toString());
            logger.info("Updating customer...");
            customerToUpdate = customer1;

            em.merge(customerToUpdate);


            em.flush();
            em.close();


            Thread.sleep(40000);

            tm.commit();

            logger.info("Update successfully persisted.");

            // customerController.deleteCustomer(1);


        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
