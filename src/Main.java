import service.CandidateService;
import service.ElectorService;
import service.PartyService;

/**
 * Created by Peixoto on 02/06/2016.
 */
public class Main {
    private static final String[] mainMenu = new String[] {"Módulo do Administrador",
                                                           "Módulo da Eleição"};
    public static void main(String[] args) {
        CandidateService candidateService = new CandidateService();
        PartyService     partyService     = new PartyService();
        ElectorService   electorService   = new ElectorService();

        printMenu("Eleições", mainMenu);
    }

    public static void printMenu(String menuTitle, String[] entries) {
        int size = menuTitle.length();
        for (String entry : entries) {
            if(entry.length() > size) size = entry.length();
        }
        size += 6;
        String middle = (repeat("═", (17*(size)/29)));

        System.out.println("╔"+middle+"╗");
        System.out.println("║"+menuTitle+repeat(" ", size-menuTitle.length())+"║");
        System.out.println("╠"+middle+"╣");
        for (int i = 0; i < entries.length; i++) {
            System.out.println("║ "+(i+1)+" - "+entries[i]+repeat(" ", size-entries[i].length()-5)+"║");
        }
        System.out.println("╚"+middle+"╝");
        System.out.print("> Opção: ");
    }

    private static String repeat(String str, int count) {
        String returnString = str;
        for (int i = 1; i < count; i++) {
            returnString += str;
        }
        return returnString;
    }
}
