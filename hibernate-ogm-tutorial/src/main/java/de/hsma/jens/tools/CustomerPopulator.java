package de.hsma.jens.tools;


import de.hsma.jens.models.Customer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;


public class CustomerPopulator {
    private static Random rand = new Random();


    public static List<Customer> populateCustomerAsList(int numberOfCustomers) {

        List<Customer> cList = new ArrayList<Customer>();


        String C_UNAME, C_PASSWD, C_LNAME, C_FNAME;
        int C_ADDR_ID;
        String C_EMAIL, C_PHONE;
        java.sql.Date C_SINCE, C_LAST_LOGIN;
        java.sql.Timestamp C_LOGIN, C_EXPIRATION;
        double C_DISCOUNT, C_BALANCE, C_YTD_PMT;
        java.sql.Date C_BIRTHDATE;
        String C_DATA;
        int i;
        // System.out.println("Populating CUSTOMER Table with " +
        //         numberOfCustomers + " customers");
        // System.out.print("Complete (in 10,000's): ");
        try {

            for (i = 1; i <= numberOfCustomers; i++) {
                Customer myCustomer = new Customer();

                if (i % 10000 == 0)
                    System.out.print(i / 10000 + " ");
                C_UNAME = DigSyl(i, 0);
                C_PASSWD = C_UNAME.toLowerCase();
                C_LNAME = getRandomAString(8, 15);
                C_FNAME = getRandomAString(8, 15);
                C_ADDR_ID = getRandomInt(1, 2 * numberOfCustomers);
                C_PHONE = getRandomAString(8, 15);
                C_EMAIL = C_UNAME + "@" + getRandomAString(2, 9) + ".com";

                GregorianCalendar cal = new GregorianCalendar();
                cal.add(Calendar.DAY_OF_YEAR, -1 * getRandomInt(1, 730));
                C_SINCE = new java.sql.Date(cal.getTime().getTime());
                cal.add(Calendar.DAY_OF_YEAR, getRandomInt(0, 60));
                if (cal.after(new GregorianCalendar()))
                    cal = new GregorianCalendar();

                C_LAST_LOGIN = new java.sql.Date(cal.getTime().getTime());
                C_LOGIN = new java.sql.Timestamp(System.currentTimeMillis());
                cal = new GregorianCalendar();
                cal.add(Calendar.HOUR, 2);
                C_EXPIRATION = new java.sql.Timestamp(cal.getTime().getTime());

                C_DISCOUNT = (double) getRandomInt(0, 50) / 100.0;
                C_BALANCE = 0.00;
                C_YTD_PMT = (double) getRandomInt(0, 99999) / 100.0;
                int year = getRandomInt(1880, 2000);
                int month = getRandomInt(0, 11);
                int maxday = 31;
                int day;
                if (month == 3 | month == 5 | month == 8 | month == 10)
                    maxday = 30;
                else if (month == 1)
                    maxday = 28;
                day = getRandomInt(1, maxday);
                cal = new GregorianCalendar(year, month, day);
                C_BIRTHDATE = new java.sql.Date(cal.getTime().getTime());

                C_DATA = getRandomAString(100, 120);

                try {// Set parameter

                    myCustomer.setC_YTD_PMT(C_YTD_PMT);
                    myCustomer.setC_ADDR_ID(C_ADDR_ID);
                    myCustomer.setC_BALANCE(C_BALANCE);
                    myCustomer.setC_BIRTHDATE(C_BIRTHDATE);
                    myCustomer.setC_DATA(C_DATA);
                    myCustomer.setC_DISCOUNT(C_DISCOUNT);
                    myCustomer.setC_EMAIL(C_EMAIL);
                    myCustomer.setC_EXPIRATION(C_EXPIRATION);
                    myCustomer.setC_FNAME(C_FNAME);
                    myCustomer.setC_ID(i);
                    myCustomer.setC_LAST_LOGIN(C_LAST_LOGIN);
                    myCustomer.setC_LNAME(C_LNAME);
                    myCustomer.setC_LOGIN(C_LOGIN);
                    myCustomer.setC_PASSWD(C_PASSWD);
                    myCustomer.setC_PHONE(C_PHONE);
                    myCustomer.setC_SINCE(C_SINCE);
                    myCustomer.setC_UNAME(C_UNAME);

                    cList.add(myCustomer);


                } catch (java.lang.Exception ex) {
                    System.err.println("Unable to populate CUSTOMER table");
                    System.out.println("C_ID=" + i + " C_UNAME=" + C_UNAME +
                            " C_PASSWD=" + C_PASSWD + " C_FNAME=" +
                            C_FNAME + " C_LNAME=" + C_LNAME + " C_ADDR_ID="
                            + C_ADDR_ID + " C_PHONE=" + C_PHONE + " C_EMAIL="
                            + C_EMAIL + " C_SINCE=" + C_SINCE + " C_LAST_LOGIN=" + C_LAST_LOGIN + " C_LOGIN= " + C_LOGIN + " C_EXPIRATION=" + C_EXPIRATION + " C_DISCOUNT=" + C_DISCOUNT + " C_BALANCE=" + C_BALANCE + " C_YTD_PMT" +
                            C_YTD_PMT + "C_BIRTHDATE=" + C_BIRTHDATE
                            + "C_DATA=" + C_DATA);
                    ex.printStackTrace();
                    System.exit(1);
                }
            }
            System.out.print("\n");
            ////con.commit();
        } catch (java.lang.Exception ex) {
            System.err.println("Unable to populate CUSTOMER table");
            ex.printStackTrace();
            System.exit(1);
        }

        return cList;

    }

    /*
    writes Customers into a file
     */
    public static void populateCustomerAsFile(int numberOfCustomers) {
        ObjectOutputStream oos = null;
        FileOutputStream fos = null;
        String writeToFile = null;


        String C_UNAME, C_PASSWD, C_LNAME, C_FNAME;
        int C_ADDR_ID;
        String C_EMAIL, C_PHONE;
        java.sql.Date C_SINCE, C_LAST_LOGIN;
        java.sql.Timestamp C_LOGIN, C_EXPIRATION;
        double C_DISCOUNT, C_BALANCE, C_YTD_PMT;
        java.sql.Date C_BIRTHDATE;
        String C_DATA;
        int i;

        try {

            fos = new FileOutputStream(Config.PERSIST_STORAGE_LOCATION);
            oos = new ObjectOutputStream(fos);


            for (i = 1; i <= numberOfCustomers; i++) {
                Customer myCustomer = new Customer();

                C_UNAME = DigSyl(i, 0);
                C_PASSWD = C_UNAME.toLowerCase();
                C_LNAME = getRandomAString(8, 15);
                C_FNAME = getRandomAString(8, 15);
                C_ADDR_ID = getRandomInt(1, 2 * numberOfCustomers);
                C_PHONE = getRandomAString(8, 15);
                C_EMAIL = C_UNAME + "@" + getRandomAString(2, 9) + ".com";

                GregorianCalendar cal = new GregorianCalendar();
                cal.add(Calendar.DAY_OF_YEAR, -1 * getRandomInt(1, 730));
                C_SINCE = new java.sql.Date(cal.getTime().getTime());
                cal.add(Calendar.DAY_OF_YEAR, getRandomInt(0, 60));
                if (cal.after(new GregorianCalendar()))
                    cal = new GregorianCalendar();

                C_LAST_LOGIN = new java.sql.Date(cal.getTime().getTime());
                C_LOGIN = new java.sql.Timestamp(System.currentTimeMillis());
                cal = new GregorianCalendar();
                cal.add(Calendar.HOUR, 2);
                C_EXPIRATION = new java.sql.Timestamp(cal.getTime().getTime());

                C_DISCOUNT = (double) getRandomInt(0, 50) / 100.0;
                C_BALANCE = 0.00;
                C_YTD_PMT = (double) getRandomInt(0, 99999) / 100.0;
                int year = getRandomInt(1880, 2000);
                int month = getRandomInt(0, 11);
                int maxday = 31;
                int day;
                if (month == 3 | month == 5 | month == 8 | month == 10)
                    maxday = 30;
                else if (month == 1)
                    maxday = 28;
                day = getRandomInt(1, maxday);
                cal = new GregorianCalendar(year, month, day);
                C_BIRTHDATE = new java.sql.Date(cal.getTime().getTime());

                C_DATA = getRandomAString(100, 120);


                myCustomer.setC_YTD_PMT(C_YTD_PMT);
                myCustomer.setC_ADDR_ID(C_ADDR_ID);
                myCustomer.setC_BALANCE(C_BALANCE);
                myCustomer.setC_BIRTHDATE(C_BIRTHDATE);
                myCustomer.setC_DATA(C_DATA);
                myCustomer.setC_DISCOUNT(C_DISCOUNT);
                myCustomer.setC_EMAIL(C_EMAIL);
                myCustomer.setC_EXPIRATION(C_EXPIRATION);
                myCustomer.setC_FNAME(C_FNAME);
                myCustomer.setC_ID(i);
                myCustomer.setC_LAST_LOGIN(C_LAST_LOGIN);
                myCustomer.setC_LNAME(C_LNAME);
                myCustomer.setC_LOGIN(C_LOGIN);
                myCustomer.setC_PASSWD(C_PASSWD);
                myCustomer.setC_PHONE(C_PHONE);
                myCustomer.setC_SINCE(C_SINCE);
                myCustomer.setC_UNAME(C_UNAME);

                oos.writeObject(myCustomer);
            }

            oos.writeObject(null);
            fos.flush();
            fos.close();
            oos.flush();
            oos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }


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

    private static String getRandomAString(int length) {
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
        for (i = 0; i < length; i++) {
            char c = chars[(int) Math.floor(rand.nextDouble() * 79)];
            newstring = newstring.concat(String.valueOf(c));
        }
        return newstring;
    }

    private static int getRandomNString(int num_digits) {
        int return_num = 0;
        for (int i = 0; i < num_digits; i++) {
            return_num += getRandomInt(0, 9) *
                    (int) java.lang.Math.pow(10.0, (double) i);
        }
        return return_num;
    }

    private static int getRandomNString(int min, int max) {
        int strlen = (int) Math.floor(rand.nextDouble() * ((max - min) + 1)) + min;
        return getRandomNString(strlen);
    }

    private static int getRandomInt(int lower, int upper) {

        int num = (int) Math.floor(rand.nextDouble() * ((upper + 1) - lower));
        if (num + lower > upper || num + lower < lower) {
            System.out.println("ERROR: Random returned value of of range!");
            System.exit(1);
        }
        return num + lower;
    }


    private static String DigSyl(int D, int N) {
        int i;
        String resultString = new String();
        String Dstr = Integer.toString(D);

        if (N > Dstr.length()) {
            int padding = N - Dstr.length();
            for (i = 0; i < padding; i++)
                resultString = resultString.concat("BA");
        }

        for (i = 0; i < Dstr.length(); i++) {
            if (Dstr.charAt(i) == '0')
                resultString = resultString.concat("BA");
            else if (Dstr.charAt(i) == '1')
                resultString = resultString.concat("OG");
            else if (Dstr.charAt(i) == '2')
                resultString = resultString.concat("AL");
            else if (Dstr.charAt(i) == '3')
                resultString = resultString.concat("RI");
            else if (Dstr.charAt(i) == '4')
                resultString = resultString.concat("RE");
            else if (Dstr.charAt(i) == '5')
                resultString = resultString.concat("SE");
            else if (Dstr.charAt(i) == '6')
                resultString = resultString.concat("AT");
            else if (Dstr.charAt(i) == '7')
                resultString = resultString.concat("UL");
            else if (Dstr.charAt(i) == '8')
                resultString = resultString.concat("IN");
            else if (Dstr.charAt(i) == '9')
                resultString = resultString.concat("NG");
        }

        return resultString;
    }
}
