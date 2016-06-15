package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

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
        System.out.print("> Opção: ");
        return pao.nextInt();
    }

    static int printMenu(String menuTitle, HashMap<Integer, String> map) {
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
        System.out.print("> Opção: ");
        return pao.nextInt();
    }

    static String repeat(String str, int count) {
        String returnString = str;
        for (int i = 1; i < count; i++) {
            returnString += str;
        }
        return returnString;
    }
}
