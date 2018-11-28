package zentino.controllers;

import org.apache.log4j.Logger;
import zentino.models.MyCustomer;
import zentino.tools.MyConfig;
import zentino.tools.MyCustomerPopulator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class MyCustomerController {
    private static Logger logger = Logger.getRootLogger();
    TransactionManager tM = com.arjuna.ats.jta.TransactionManager.transactionManager();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory(MyConfig.PERSISTENCE_UNIT_NAME);

    public void createCustomers() {
        List<MyCustomer> myCustomers = MyCustomerPopulator.populateCustomerAsList(MyConfig.NUMBER_OF_CUSTOMERS);

        try {
            logger.info("Create customers TA begins");
            EntityManager em = emf.createEntityManager();
            tM.setTransactionTimeout(MyConfig.TRANSACTION_TIMEOUT);
            tM.begin();

            long queryStart = System.currentTimeMillis();

            for (MyCustomer c : myCustomers) {
                em.persist(c);
            }

            long queryEnd = System.currentTimeMillis();

            em.flush();
            em.close();
            tM.commit();

            logger.info("Create customers TA ends");

            long queryTime = queryEnd - queryStart;

            logger.info(myCustomers.size() + " customers persisted in DB in " + queryTime + " ms.");

            String writeToFile = new String(MyConfig.PERSISTENCE_UNIT_NAME + " CREATE: " + myCustomers.size() + " " + queryTime + "\n");

            Files.write(Paths.get(MyConfig.LOG_STORAGE_LOCATION), writeToFile.getBytes(), CREATE, APPEND);


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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomer(Long myCustomerID) {

        try {
            //logger.info("Delete customer TA begins");
            EntityManager em = emf.createEntityManager();
            tM.setTransactionTimeout(MyConfig.TRANSACTION_TIMEOUT);
            tM.begin();


            // long queryStart = System.currentTimeMillis();

            MyCustomer cust = em.find(MyCustomer.class, myCustomerID);
            // logger.info("Found customer: " + cust.toString());
            // logger.info("Deleting customer...");
            em.remove(cust);
            //  long queryEnd = System.currentTimeMillis();
            em.flush();
            em.close();
            tM.commit();
            //logger.info("Delete customer TA ends");

            //  long queryTime = queryEnd - queryStart;

            // logger.info("Customer successfully deleted in " + queryTime + " ms.");


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
        }
    }

    public void deleteAllCustomer() {

        List<Long> myCustomerIDs;

        try {
            myCustomerIDs = getAllCustomerIDs();
            logger.info("Delete all customers TA begins");
            long queryStart = System.currentTimeMillis();

            for (Long id : myCustomerIDs) {
                deleteCustomer(id);
            }

            long queryEnd = System.currentTimeMillis();

            logger.info("Delete all customers TA ends");
            long queryTime = queryEnd - queryStart;

            logger.info(myCustomerIDs.size() + " customers successfully deleted in " + queryTime + " ms.");

            String writeToFile = new String(MyConfig.PERSISTENCE_UNIT_NAME + " DELETE: " + myCustomerIDs.size() + " " + queryTime + "\n");
            Files.write(Paths.get(MyConfig.LOG_STORAGE_LOCATION), writeToFile.getBytes(), CREATE, APPEND);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Long> getAllCustomerIDs() {

        List<MyCustomer> myCustomerIDs = new ArrayList<>();
        List<Long> returnList = new ArrayList<>();

        try {
            EntityManager em = emf.createEntityManager();

            //String queryString = new String("SELECT c.C_ID FROM Customer c");
            /*
            some datastores (that uses indexes like Redis, Infinispan, Cassandra) cannot operate on single return values,
            they always need to return the entire class
             */

            String queryString = new String("SELECT c FROM MyCustomer c");
            Query q = em.createQuery(queryString);


            logger.info("Get all customerIDs TA begins");
            tM.setTransactionTimeout(MyConfig.TRANSACTION_TIMEOUT);
            tM.begin();

            long queryStart = System.currentTimeMillis();

            myCustomerIDs = q.getResultList();

            for (MyCustomer c : myCustomerIDs) {
                returnList.add(c.getC_ID());
            }

            long queryEnd = System.currentTimeMillis();

            em.flush();
            em.close();
            tM.commit();
            logger.info("Get all customerIDs TA ends");

            long queryTime = queryEnd - queryStart;

            logger.info("Found " + myCustomerIDs.size() + " customer IDs in " + queryTime + " ms.");


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
        }
        return returnList;
    }

    public MyCustomer getCustomer(Long myCustomerID) {
        MyCustomer myCustomer = new MyCustomer();

        try {
            EntityManager em = emf.createEntityManager();
            tM.begin();
            myCustomer = em.find(MyCustomer.class, myCustomerID);
            em.flush();
            em.close();
            tM.commit();
        }catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        }
        return myCustomer;
    }
}
