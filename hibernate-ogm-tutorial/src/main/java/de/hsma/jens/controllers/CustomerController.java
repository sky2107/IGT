package de.hsma.jens.controllers;


import de.hsma.jens.models.Customer;
import de.hsma.jens.tools.Config;
import de.hsma.jens.tools.CustomerPopulator;
import org.apache.log4j.Logger;
import org.hibernate.CacheMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.*;
import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

/**
 * Created by jenskohler on 12.12.17.
 */
public class CustomerController {
    private static Logger logger = Logger.getRootLogger();
    //accessing JBoss's Transaction can be done differently but this one works nicely
    TransactionManager tm = com.arjuna.ats.jta.TransactionManager.transactionManager();
    //build the EntityManagerFactory as you would build in in Hibernate ORM
    EntityManagerFactory emf = Persistence.createEntityManagerFactory(Config.PERSISTENCE_UNIT_NAME);

    public void createCustomers() {


        /*
        Customer customer1 = new Customer();

        customer1.setC_ADDR_ID(1);
        customer1.setC_BALANCE(99.9);
        customer1.setC_BIRTHDATE(new Date());
        customer1.setC_DATA("data_1");
        customer1.setC_DISCOUNT(99.9);
        customer1.setC_EMAIL("email_1");
        customer1.setC_EXPIRATION(new Date());
        customer1.setC_FNAME("fname_1");
        customer1.setC_ID(1);
        customer1.setC_LAST_LOGIN(new Date());
        customer1.setC_LOGIN(new Date());
        customer1.setC_PASSWD("password_1");
        customer1.setC_LNAME("lname_1");
        customer1.setC_PHONE("phone_1");
        customer1.setC_SINCE(new Date());
        customer1.setC_YTD_PMT(99.9);
        customer1.setC_UNAME("uname_1");
        */


        List<Customer> cList = CustomerPopulator.populateCustomerAsList(Config.NUMBER_OF_CUSTOMERS);


        try {
            logger.info("Create customers TA begins");
            EntityManager em = emf.createEntityManager();
            tm.setTransactionTimeout(Config.TRANSACTION_TIMEOUT);
            tm.begin();

            long queryStart = System.currentTimeMillis();
            //em.persist(customer1);


            for (Customer c : cList) {
                em.persist(c);
            }

            long queryEnd = System.currentTimeMillis();

            em.flush();
            em.close();
            tm.commit();

            logger.info("Create customers TA ends");

            long queryTime = queryEnd - queryStart;

            logger.info(cList.size() + " customers persisted in DB in " + queryTime + " ms.");

            String writeToFile = new String(Config.PERSISTENCE_UNIT_NAME + " CREATE: " + cList.size() + " " + queryTime + "\n");

            Files.write(Paths.get(Config.LOG_STORAGE_LOCATION), writeToFile.getBytes(), CREATE, APPEND);


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




    public void updateCustomer(Customer c) {

        try {
            logger.info("Update customer TA begins");
            EntityManager em = emf.createEntityManager();
            tm.setTransactionTimeout(Config.TRANSACTION_TIMEOUT);
            tm.begin();


            long queryStart = System.currentTimeMillis();

            Customer customerToUpdate = em.find(Customer.class, c.getC_ID());
            logger.info("Found customer: " + customerToUpdate.toString());
            logger.info("Updating customer...");
            customerToUpdate = c;

            em.merge(customerToUpdate);

            long queryEnd = System.currentTimeMillis();


            em.flush();
            em.close();
            tm.commit();
            logger.info("Update customer TA ends");

            long queryTime = queryEnd - queryStart;

            logger.info("Customer successfully persisted in " + queryTime + " ms.");

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

    public void updateCustomer(List<Customer> cList) {

        try {
            EntityManager em = emf.createEntityManager();
            logger.info("Update customers TA begins");
            tm.setTransactionTimeout(Config.TRANSACTION_TIMEOUT);
            tm.begin();

            long queryStart = System.currentTimeMillis();

            for (Customer c : cList) {

                em.merge(c);

            }

            long queryEnd = System.currentTimeMillis();


            em.flush();
            em.close();
            tm.commit();
            logger.info("Update customers TA ends");

            long queryTime = queryEnd - queryStart;

            logger.info("Updates of " + cList.size() + " customers successfully persisted in " + queryTime + " ms.");

            String writeToFile = new String(Config.PERSISTENCE_UNIT_NAME + " UPDATE: " + cList.size() + " " + queryTime + "\n");
            Files.write(Paths.get(Config.LOG_STORAGE_LOCATION), writeToFile.getBytes(), CREATE, APPEND);


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

    public void deleteCustomer(int customerID) {

        try {
            //logger.info("Delete customer TA begins");
            EntityManager em = emf.createEntityManager();
            tm.setTransactionTimeout(Config.TRANSACTION_TIMEOUT);
            tm.begin();


            // long queryStart = System.currentTimeMillis();

            Customer cust = em.find(Customer.class, customerID);
            // logger.info("Found customer: " + cust.toString());
            // logger.info("Deleting customer...");


            em.remove(cust);

            //  long queryEnd = System.currentTimeMillis();


            em.flush();
            em.close();
            tm.commit();
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

    public List<Customer> getAllCustomer() {


        List<Customer> cust = new ArrayList<Customer>();


        try {
            EntityManager em = emf.createEntityManager();

            String queryString = new String("SELECT c FROM Customer c");

            logger.info("Get all customer TA begins");
            tm.setTransactionTimeout(Config.TRANSACTION_TIMEOUT);
            tm.begin();


            Query q = em.createQuery(queryString);

            long queryStart = System.currentTimeMillis();

            cust = q.getResultList();

            long queryEnd = System.currentTimeMillis();


            em.flush();
            em.close();
            tm.commit();

            logger.info("Get all customer TA ends");

            long queryTime = queryEnd - queryStart;

            logger.info("Found " + cust.size() + " customers in " + queryTime + " ms.");

            String writeToFile = new String(Config.PERSISTENCE_UNIT_NAME + " READ  : " + cust.size() + " " + queryTime + "\n");
            Files.write(Paths.get(Config.LOG_STORAGE_LOCATION), writeToFile.getBytes(), CREATE, APPEND);


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


        return cust;


    }



    public void deleteAllCustomer() {

        List<Integer> cust;

        try {


            cust = getAllCustomerIDs();


            logger.info("Delete all customers TA begins");


            long queryStart = System.currentTimeMillis();

            for (Integer id : cust) {

                deleteCustomer(id);
            }


            long queryEnd = System.currentTimeMillis();


            logger.info("Delete all customers TA ends");
            long queryTime = queryEnd - queryStart;

            logger.info(cust.size() + " customers successfully deleted in " + queryTime + " ms.");

            String writeToFile = new String(Config.PERSISTENCE_UNIT_NAME + " DELETE: " + cust.size() + " " + queryTime + "\n");
            Files.write(Paths.get(Config.LOG_STORAGE_LOCATION), writeToFile.getBytes(), CREATE, APPEND);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Double calculateAverageCustomerRevenue() {
        Double averageRevenue = -1.;

        ArrayList<Customer> cList = (ArrayList) getAllCustomer();

        for (Customer c : cList) {
            averageRevenue += c.getC_YTD_PMT();
        }

        averageRevenue /= cList.size();


        return averageRevenue;

    }

    public List<Integer> getAllCustomerIDs() {

        List<Customer> cIDs = new ArrayList<Customer>();
        List<Integer> returnList = new ArrayList<Integer>();

        try {
            EntityManager em = emf.createEntityManager();

            //String queryString = new String("SELECT c.C_ID FROM Customer c");
            /*
            some datastores (that uses indexes like Redis, Infinispan, Cassandra) cannot operate on single return values,
            they always need to return the entire class
             */

            String queryString = new String("SELECT c FROM Customer c");
            Query q = em.createQuery(queryString);


            logger.info("Get all customerIDs TA begins");
            tm.setTransactionTimeout(Config.TRANSACTION_TIMEOUT);
            tm.begin();

            long queryStart = System.currentTimeMillis();

            cIDs = q.getResultList();

           for (Customer c : cIDs) {
               returnList.add(c.getC_ID());
           }


            long queryEnd = System.currentTimeMillis();


            em.flush();
            em.close();
            tm.commit();
            logger.info("Get all customerIDs TA ends");

            long queryTime = queryEnd - queryStart;

            logger.info("Found " + cIDs.size() + " customer IDs in " + queryTime + " ms.");


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

    public Customer getCustomer(int customerID) {


        Customer cust = null;


        try {
            EntityManager em = emf.createEntityManager();
            tm.begin();


            cust = em.find(Customer.class, customerID);
            // logger.info("Found customer: " + cust.toString());


            em.flush();
            em.close();
            tm.commit();
            // logger.info("TA ends");


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


        return cust;


    }




    public void createCustomersFromFile() {

        CustomerPopulator.populateCustomerAsFile(Config.NUMBER_OF_CUSTOMERS);
        ObjectInputStream ois = null;
        Customer c = null;

        try {
            Path path = Paths.get(URI.create("file:///" + Config.PERSIST_STORAGE_LOCATION));
            FileInputStream fin = new FileInputStream(Config.PERSIST_STORAGE_LOCATION);
            byte[] data = Files.readAllBytes(path);
            ois = new ObjectInputStream(new ByteArrayInputStream(data));
            logger.info("Create customers TA begins");
            EntityManager em = emf.createEntityManager();
            tm.setTransactionTimeout(Config.TRANSACTION_TIMEOUT);
            tm.begin();

            long queryStart = System.currentTimeMillis();



            /*
            In order for the object stream not to use the references mechanism the I/O methods:

            writeObject()
            readObject()

            need to be replaced by the methods:

            writeUnshared()
            readUnshared()

            readUnshared(), unlike readObject(), will not save a reference in ObjectInputStream’s references table, and so the memory leak will be prevented. It should be noted that if the object transferred has implemented the readResolve() method (from Serializable interface) it could be that a reference to the object is passed and saved in there. The holder of the references could also be analyzed by a heap dump in VisualVM.
            Another solution is to invoke the ObjectOutputStream reset() method after each write. This will have the same effect.

            https://orenkishon.wordpress.com/2014/06/28/java-memory-leak-caused-by-objectinputstream/
             */


            do {
                c = (Customer) ois.readObject();
                if (c != null) {
                    em.persist(c);
                    //logger.info(c.toString());
                    // ois.reset();

                    if (c.getC_ID() % (Config.NUMBER_OF_CUSTOMERS / 10) == 0) {
                        logger.info("Flushed after C_ID: " + c.getC_ID());
                        em.flush();
                        em.clear();
                    }

                }

            }
            while (c != null);


            long queryEnd = System.currentTimeMillis();

            em.flush();
            em.close();
            tm.commit();

            logger.info("Create customers TA ends");

            long queryTime = queryEnd - queryStart;

            logger.info(Config.NUMBER_OF_CUSTOMERS + " customers persisted in DB in " + queryTime + " ms.");

            String writeToFile = new String(Config.PERSISTENCE_UNIT_NAME + " CREATE: " + Config.NUMBER_OF_CUSTOMERS + " " + queryTime + "\n");

            Files.write(Paths.get(Config.LOG_STORAGE_LOCATION), writeToFile.getBytes(), CREATE, APPEND);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


    public void updateCustomersFromFile() {


        ObjectInputStream ois = null;
        Customer c = null;

        try {
            logger.info("Update customers TA begins");
            EntityManager em = emf.createEntityManager();
            tm.setTransactionTimeout(Config.TRANSACTION_TIMEOUT);
            tm.begin();

            long queryStart = System.currentTimeMillis();

            ois = new ObjectInputStream(new FileInputStream(Config.PERSIST_STORAGE_LOCATION));


            do {
                c = (Customer) ois.readObject();

                if (c != null) {
                    Customer customerOriginal = em.find(Customer.class, c.getC_ID());
                    if (customerOriginal != null) {
                        em.merge(c);
                    }

                    //logger.info(c.toString());
                }

            }
            while (c != null);


            long queryEnd = System.currentTimeMillis();

            em.flush();
            em.close();
            tm.commit();

            logger.info("Create customers TA ends");

            long queryTime = queryEnd - queryStart;

            logger.info(Config.NUMBER_OF_CUSTOMERS + " customers persisted in DB in " + queryTime + " ms.");

            String writeToFile = new String(Config.PERSISTENCE_UNIT_NAME + " CREATE: " + Config.NUMBER_OF_CUSTOMERS + " " + queryTime + "\n");

            Files.write(Paths.get(Config.LOG_STORAGE_LOCATION), writeToFile.getBytes(), CREATE, APPEND);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }



    public Integer getAllCustomerAsList() {


        ObjectOutputStream oos = null;
        FileOutputStream fos = null;
        List<Integer> numberOfCustomers = null;


        try {


            fos = new FileOutputStream(Config.PERSIST_STORAGE_OUTPUT_LOCATION);
            oos = new ObjectOutputStream(fos);

            EntityManager em = emf.createEntityManager();


            String queryString = new String("FROM Customer");


            javax.persistence.Query query = em.createQuery(queryString);

            Session session = em.unwrap(Session.class);

            /*
            Hibernate OGM: not implemented yet
             */

            ScrollableResults results = session
                    .createQuery("FROM Customer")
                    .setCacheMode(CacheMode.IGNORE)
                    .scroll(ScrollMode.FORWARD_ONLY);


            numberOfCustomers = getAllCustomerIDs();

            logger.info("Get all customer TA begins");
            tm.setTransactionTimeout(Config.TRANSACTION_TIMEOUT);
            tm.begin();


            long queryStart = System.currentTimeMillis();


            while (results.next()) {
                Customer c = (Customer) results.get(0);
                c.toString();
            }
            results.close();


            long queryEnd = System.currentTimeMillis();


            oos.writeObject(null);
            fos.flush();
            fos.close();
            oos.flush();
            oos.close();

            em.flush();
            em.close();
            tm.commit();

            logger.info("Get all customer TA ends");

            long queryTime = queryEnd - queryStart;

            logger.info("Found " + numberOfCustomers.size() + " customers in " + queryTime + " ms.");

            String writeToFile = new String(Config.PERSISTENCE_UNIT_NAME + " READ  : " + numberOfCustomers.size() + " " + queryTime + "\n");
            Files.write(Paths.get(Config.LOG_STORAGE_LOCATION), writeToFile.getBytes(), CREATE, APPEND);


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

        return numberOfCustomers.size();


    }



}
