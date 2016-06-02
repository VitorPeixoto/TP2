package service;

import java.util.Scanner;
import java.util.regex.Pattern;

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
}
