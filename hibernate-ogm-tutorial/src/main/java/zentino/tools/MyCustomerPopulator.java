package zentino.tools;

import zentino.models.MyCustomer;
import zentino.models.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MyCustomerPopulator {
    public static Random rand = new Random();

    public static List<MyCustomer> populateCustomerAsList(int numberOfCustomers) {

        Integer C_ADDR_ID;
        String C_STATUS;
        Double C_FLOWN_MILES;
        Double C_CURRENT_FLOWN_MILES;
        String C_PHONE;

        List<MyCustomer> cList = new ArrayList<>();

        try {
            for (int k = 0; k < numberOfCustomers; k++) {
                MyCustomer customer = new MyCustomer();
                C_ADDR_ID = k;
                int randomInt = ThreadLocalRandom.current().nextInt(6);
                C_STATUS = Status.values()[randomInt].name();
                C_FLOWN_MILES = Math.random() * 1337;
                C_CURRENT_FLOWN_MILES = Math.random() * 577;
                C_PHONE = getRandomPhoneNumber(7, 17);

                customer.setC_ID(Long.valueOf(k));
                customer.setC_ADDR_ID(C_ADDR_ID);
                customer.setC_STATUS(C_STATUS);
                customer.setC_FLOWN_MILES(C_FLOWN_MILES);
                customer.setC_CURRENT_FLOWN_MILES(C_CURRENT_FLOWN_MILES);
                customer.setC_PHONE(C_PHONE);
                cList.add(customer);

            }
        } catch (java.lang.Exception ex){
            System.err.println("Unable to populate CUSTOMER table");
            ex.printStackTrace();
            System.exit(1);
        }
        return cList;
    }

    private static String getRandomPhoneNumber(int min, int max) {
        String newstring = new String();
        int i;
        final char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'}; //10 characters
        int strlen = (int) Math.floor(rand.nextDouble() * ((max - min) + 1));
        strlen += min;
        for (i = 0; i < strlen; i++) {
            char c = chars[(int) Math.floor(rand.nextDouble() * 10)];
            newstring = newstring.concat(String.valueOf(c));
        }
        return newstring;

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
