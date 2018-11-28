package zentino.transactions;

import zentino.tools.MyConfig;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.TransactionManager;

public class Test {
    static TransactionManager tm = com.arjuna.ats.jta.TransactionManager.transactionManager();
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory(MyConfig.PERSISTENCE_UNIT_NAME);


    public static void main(String[] args) {

    }
}
