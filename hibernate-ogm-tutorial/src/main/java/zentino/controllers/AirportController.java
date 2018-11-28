package zentino.controllers;

import org.apache.log4j.Logger;
import zentino.models.Airport;
import zentino.tools.AirportPopulator;
import zentino.tools.MyConfig;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class AirportController {
    private static Logger logger = Logger.getRootLogger();
    TransactionManager tM = com.arjuna.ats.jta.TransactionManager.transactionManager();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory(MyConfig.PERSISTENCE_UNIT_NAME);

    public Airport getAirport(Long id) {
        Airport airport = new Airport();

        try {
            EntityManager em = emf.createEntityManager();
            tM.begin();
            airport = em.find(Airport.class, id);
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
        return airport;
    }

    public void createAirportsAsList() {
        List<Airport> airports = AirportPopulator.populateAirportsAsList(MyConfig.NUMBER_OF_AIRPORTS);
        try {
            logger.info("Create airports TA begins");
            EntityManager em = emf.createEntityManager();
            tM.setTransactionTimeout(MyConfig.TRANSACTION_TIMEOUT);
            tM.begin();
            long queryStart = System.currentTimeMillis();

            for (Airport a : airports) {
                em.persist(a);
            }
            long queryEnd = System.currentTimeMillis();

            em.flush();
            em.close();
            tM.commit();

            logger.info("Create airports TA ends");

            long queryTime = queryEnd - queryStart;

            logger.info(airports.size() + " airports persisted in DB in " + queryTime + " ms.");

            String writeToFile = new String(MyConfig.PERSISTENCE_UNIT_NAME + " CREATE: " + airports.size() + " " + queryTime + "\n");

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

}

