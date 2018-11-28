package zentino.tools;

public class MyConfig {

    public static final Integer NUMBER_OF_CUSTOMERS = 100;
    public static final Integer NUMBER_OF_FLIGHTS = 100;
    public static final Integer NUMBER_OF_AIRPORTS = 30;
    public static final Integer NUMBER_OF_FLIGHTSEGMENTS = 20;
    public static final String PERSISTENCE_UNIT_NAME = de.hsma.jens.tools.Config.PERSISTENCE_UNITS.OGM_MYSQL.name();
    public static final String LOG_STORAGE_LOCATION = "/home/santi/Downloads/" + PERSISTENCE_UNIT_NAME + "_crud_performance.txt";
    public static final Integer TRANSACTION_TIMEOUT = 900000;
    public static final String PERSIST_STORAGE_LOCATION = "/home/santi/Downloads/customers.txt";
    public static final String PERSIST_STORAGE_OUTPUT_LOCATION = "/home/santi/Downloads/customers_out.txt";

    public enum PERSISTENCE_UNITS {
        OGM_MYSQL, OGM_POSTGRESQL, OGM_MONGODB, OGM_NEO4J, OGM_INFINISPAN, OGM_CASSANDRA, OGM_REDIS,
    }

}
