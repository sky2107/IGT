package zentino.transactions;

import org.apache.log4j.Logger;
import zentino.tools.MyConfig;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.TransactionManager;

public class BookFlightTransaction {
    static TransactionManager tm = com.arjuna.ats.jta.TransactionManager.transactionManager();
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory(MyConfig.PERSISTENCE_UNIT_NAME);
    private static Logger logger = Logger.getRootLogger();

    public static void main(String[] args) {

    }
}
