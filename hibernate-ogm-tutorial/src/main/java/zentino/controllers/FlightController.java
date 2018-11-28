package zentino.controllers;

import org.apache.log4j.Logger;
import zentino.models.Flight;
import zentino.tools.MyConfig;
import zentino.tools.MyFlightPopulator;

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

public class FlightController {
    private static Logger logger = Logger.getRootLogger();
    TransactionManager tM = com.arjuna.ats.jta.TransactionManager.transactionManager();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory(MyConfig.PERSISTENCE_UNIT_NAME);

    public void createFlightsAsList() {
        List<Flight> fList = MyFlightPopulator.populateFlightsAsList(MyConfig.NUMBER_OF_FLIGHTS);
        try {
            logger.info("Create flights TA begins");
            EntityManager em = emf.createEntityManager();
            tM.setTransactionTimeout(MyConfig.TRANSACTION_TIMEOUT);
            tM.begin();
            long queryStart = System.currentTimeMillis();

            for (Flight f : fList) {
                em.persist(f);
            }
            long queryEnd = System.currentTimeMillis();

            em.flush();
            em.close();
            tM.commit();

            logger.info("Create customers TA ends");

            long queryTime = queryEnd - queryStart;

            logger.info(fList.size() + " flights persisted in DB in " + queryTime + " ms.");

            String writeToFile = new String(MyConfig.PERSISTENCE_UNIT_NAME + " CREATE: " + fList.size() + " " + queryTime + "\n");

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

    public Flight getFlight(Long flightID) {
        Flight flight = new Flight();

        try {
            EntityManager em = emf.createEntityManager();
            tM.begin();
            flight = em.find(Flight.class, flightID);
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
        return flight;
    }

    public List<Long> getAllFlightIDs() {
        List<Flight> flightIDs = new ArrayList<>();
        List<Long> returnList = new ArrayList<>();

        try {
            EntityManager em = emf.createEntityManager();

            //String queryString = new String("SELECT f.F_ID FROM Flight f");
            /*
            some datastores (that uses indexes like Redis, Infinispan, Cassandra) cannot operate on single return values,
            they always need to return the entire class
             */

            String queryString = new String("SELECT f FROM Flight f");
            Query q = em.createQuery(queryString);


            logger.info("Get all flightIDs TA begins");
            tM.setTransactionTimeout(MyConfig.TRANSACTION_TIMEOUT);
            tM.begin();

            long queryStart = System.currentTimeMillis();

            flightIDs = q.getResultList();

            for (Flight f : flightIDs) {
                returnList.add(f.getF_ID());
            }

            long queryEnd = System.currentTimeMillis();

            em.flush();
            em.close();
            tM.commit();
            logger.info("Get all flightIDs TA ends");

            long queryTime = queryEnd - queryStart;

            logger.info("Found " + flightIDs.size() + " flight IDs in " + queryTime + " ms.");


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

    public void deleteFlight(Long flightID){
        try {
            EntityManager em = emf.createEntityManager();
            tM.setTransactionTimeout(MyConfig.TRANSACTION_TIMEOUT);
            tM.begin();

            Flight flight = em.find(Flight.class, flightID);
            em.remove(flight);
            em.flush();
            em.close();
            tM.commit();

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

    public void deleteAllFlights(){
        List<Long> flightIDs;

        try {
            flightIDs = getAllFlightIDs();
            logger.info("Delete all flightsTA begins");
            long queryStart = System.currentTimeMillis();

            for (Long id : flightIDs) {
                deleteFlight(id);
            }

            long queryEnd = System.currentTimeMillis();

            logger.info("Delete all flights TA ends");
            long queryTime = queryEnd - queryStart;

            logger.info(flightIDs.size() + " flights successfully deleted in " + queryTime + " ms.");

            String writeToFile = new String(MyConfig.PERSISTENCE_UNIT_NAME + " DELETE: " + flightIDs.size() + " " + queryTime + "\n");
            Files.write(Paths.get(MyConfig.LOG_STORAGE_LOCATION), writeToFile.getBytes(), CREATE, APPEND);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
