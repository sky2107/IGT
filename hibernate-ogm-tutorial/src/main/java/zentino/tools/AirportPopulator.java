package zentino.tools;

import zentino.models.Airport;

import java.util.ArrayList;
import java.util.List;

public class AirportPopulator {

    public static List<Airport> populateAirportsAsList(int numberOfAirports) {

        Airport airport = new Airport();
        List<Airport> aList = new ArrayList<>();


        try {
            for(int k = 1; k < numberOfAirports; k++) {
                airport.setA_ID((long)k);
                airport.setA_NAME(("Airport_" + k));
                airport.setA_ADDR("Address_" + 1);
            }

        } catch (java.lang.Exception ex){
            System.err.println("Unable to populate airport table");
            ex.printStackTrace();
            System.exit(1);
        }
        return aList;
    }
}
