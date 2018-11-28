package zentino.tools;

import zentino.models.Flight;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MyFlightPopulator {
    public static Random rand = new Random();

    public static List<Flight> populateFlightsAsList(int numberOfFlights) {

        String F_AIRPLANE_TYPE;
        Date F_DEPARTURE;
        Date F_ARRIVAL;
        Integer F_EC_SEATS;
        Double F_EC_PRICE;
        Integer F_FC_SEATS;
        Double F_FC_PRICE;

        Flight flight = new Flight();
        List<Flight> fList = new ArrayList<>();


        try {
            for(int k = 1; k < numberOfFlights; k++) {
                F_AIRPLANE_TYPE = getRandomAString(7, 17);
                F_DEPARTURE = new Date();
                F_ARRIVAL = new Date(F_DEPARTURE.getTime() + 10000000);
                F_EC_SEATS = 100;
                F_FC_SEATS = 20;
                F_EC_PRICE = 150.0;
                F_FC_PRICE = 370.0;

                flight.setF_AIRPLANE_TYPE(F_AIRPLANE_TYPE);
                flight.setF_DEPARTURE(F_DEPARTURE);
                flight.setF_ARRIVAL(F_ARRIVAL);
                flight.setF_EC_SEATS(F_EC_SEATS);
                flight.setF_FC_SEATS(F_FC_SEATS);
                flight.setF_EC_PRICE(F_EC_PRICE);
                flight.setF_FC_PRICE(F_FC_PRICE);
                // TODO add Flightsegments

                fList.add(flight);
            }
            //flight.setFlightsegments(flightsegments);


        } catch (java.lang.Exception ex){
            System.err.println("Unable to populate flight table");
            ex.printStackTrace();
            System.exit(1);
        }
        return fList;
    }

    private static String getRandomAString(int min, int max) {
        String newstring = new String();
        int i;
        final char[] chars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
                'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
                'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
                'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '!', '@', '#',
                '$', '%', '^', '&', '*', '(', ')', '_', '-', '=', '+',
                '{', '}', '[', ']', '|', ':', ';', ',', '.', '?', '/',
                '~', ' '}; //79 characters
        int strlen = (int) Math.floor(rand.nextDouble() * ((max - min) + 1));
        strlen += min;
        for (i = 0; i < strlen; i++) {
            char c = chars[(int) Math.floor(rand.nextDouble() * 79)];
            newstring = newstring.concat(String.valueOf(c));
        }
        return newstring;
    }
}