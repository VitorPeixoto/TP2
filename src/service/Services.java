package service;

import java.util.*;

/**
 * Created by Peixoto on 24/05/2016.
 */
public interface Services {
    Scanner pao = new Scanner(System.in);

    void register();
    void delete();
    void list();
    void search();
    void update();

    default String brazilianToAmerican(String brasilianFormatedString) {
        return brasilianFormatedString.substring(3, 5) + "/" + brasilianFormatedString.substring(0, 2) + "/" + brasilianFormatedString.substring(6, brasilianFormatedString.length());
    }

    static int printMenu(String menuTitle, String[] entries) {
        int size        = 30,
            spacingSize = size + 18;

        String middle = (repeat("═", size));

        System.out.println("╔"+middle+"╗");
        System.out.println("║"+menuTitle+repeat(" ", spacingSize-menuTitle.length())+"║");
        System.out.println("╠"+middle+"╣");
        for (int i = 0; i < entries.length; i++) {
            System.out.println("║ "+(i+1)+" - "+entries[i]+repeat(" ", spacingSize-entries[i].length()-5)+"║");
        }
        System.out.println("╚"+middle+"╝");

        return readInteger("> Opção: ");
    }

    /**
     * Recursive try to read an input until it's an Integer
     * @return  an option Integer
     */
    static int readInteger(String question) {
        try {
            System.out.print(question);
            return pao.nextInt();
        } catch (InputMismatchException ex) {
            pao.next();
            return readInteger(question);
        }
    }

    /**
     * Recursive try to read an input String until it' represents an Integer
     * @return  an option String
     */
    static String readIntegerAsString(String question) {
        try {
            System.out.print(question);
            String input =  pao.next();
            Integer.parseInt(input);
            return input;
        } catch (NumberFormatException ex) {
            pao.next();
            return readIntegerAsString(question);
        }
    }

    /**
     * Recursive try to read an input until it's an valid String Date
     * @return  an option String Date
     */
    static String readDateAsString(String question) {
        try {
            System.out.print(question);
            String input = pao.next();
            new Date(input);
            return input;
        } catch (IllegalArgumentException ex) {
            pao.next();
            return readDateAsString(question);
        }
    }


    static String readDateAsString(String question, String whiteCard) {
        try {
            System.out.print(question);
            String input = pao.next();
            if(input.equals("-1")) return input;
            new Date(input);
            return input;
        } catch (IllegalArgumentException ex) {
            pao.next();
            return readDateAsString(question);
        }
    }

    static void printMenu(String menuTitle, HashMap<Integer, String> map) {
        int size        = 30,
            spacingSize = size + 18;

        String middle = (repeat("═", size));

        System.out.println("╔"+middle+"╗");
        System.out.println("║"+menuTitle+repeat(" ", spacingSize-menuTitle.length())+"║");
        System.out.println("╠"+middle+"╣");
        for (Integer i : map.keySet()) {
            System.out.println("║ "+i+" - "+map.get(i)+repeat(" ", spacingSize-map.get(i).length()-5)+"║");
        }
        System.out.println("╚"+middle+"╝");
    }

    static String repeat(String str, int count) {
        String returnString = str;
        for (int i = 1; i < count; i++) {
            returnString += str;
        }
        return returnString;
    }
}
