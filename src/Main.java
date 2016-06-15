import entities.Election;
import service.CandidateService;
import service.ElectorService;
import service.PartyService;
import service.Services;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Peixoto on 02/06/2016.
 */
public class Main {
    private static Scanner pao = Services.pao;
    public static CandidateService candidateService = CandidateService.getSingleService();
    public static PartyService     partyService     = PartyService.getSingleService();
    public static ElectorService   electorService   = ElectorService.getSingleService();

    private static HashMap<Integer, Election> elections = new HashMap<>();

    private static final String[] mainMenuEntries = new String[] {"Módulo do Administrador",
                                                                  "Módulo da Eleição",
                                                                  "Sair"};
    private static final String[] managerMenuEntries = new String[] {"Partidos",
                                                                     "Candidatos",
                                                                     "Eleitores",
                                                                     "Relatórios",
                                                                     "Menu Principal"};
    private static final String[] electionMenuEntries = new String[] {"Começar uma Eleição",
                                                                      "Menu Principal"};
    private static final String[] servicesMenuEntries = new String[] {"Registrar",
                                                                      "Excluir",
                                                                      "Listar",
                                                                      "Pesquisar",
                                                                      "Alterar",
                                                                      "Menu Anterior"};

    public static void main(String[] args) {
        int option = 0;
        while(option != 3) {
            option = Services.printMenu("Eleições", mainMenuEntries);

            switch (option) {
                case 1:
                    managerMenu();
                    break;
                case 2:
                    electionMenu();
                    break;
            }
        }
    }

    private static void electionMenu() {
        int electionOption = 0;
        while(electionOption != 2) {
            electionOption = Services.printMenu("Eleição", electionMenuEntries);

            switch (electionOption) {
                case 1:
                    System.out.println("Entre com os dados do presidente:");

                    System.out.println("Nome do presidente: ");
                    String presidentName = pao.next();

                    System.out.println("Data de nascimento: ");
                    String presidentBirth = pao.next();

                    System.out.println("Título: ");
                    String presidentTitle = pao.next();

                    System.out.println("Zona: ");
                    int presidentZone = pao.nextInt();

                    System.out.println("Seção: ");
                    int presidentSection = pao.nextInt();
                    Election election = new Election(presidentName, presidentZone, presidentSection, presidentTitle, presidentBirth,
                                                     electorService.getNumberOfElectorsByZoneAndSection(presidentZone, presidentSection));

                    //Adiciona eleição ao HashMap
                    elections.put(presidentSection, election);
                    break;
                case 2:
                    break;
            }
        }
    }

    private static void managerMenu() {
        int managerOption = 0,
            serviceOption = 0;
        while(managerOption != 5) {
            managerOption = Services.printMenu("Administração", managerMenuEntries);

            switch (managerOption) {
                case 1:
                    serviceOption = Services.printMenu("Partidos", servicesMenuEntries);
                    callService("Partidos", partyService, serviceOption);
                    break;
                case 2:
                    serviceOption = Services.printMenu("Candidatos", servicesMenuEntries);
                    callService("Candidatos", candidateService, serviceOption);
                    break;
                case 3:
                    serviceOption = Services.printMenu("Eleitores", servicesMenuEntries);
                    callService("Eleitores", electorService, serviceOption);
                    break;
                case 4:
                    //@TODO fazer os relatórios
                    break;
            }
        }
    }

    private static void callService(String serviceName, Services service, int serviceOption) {
        while(serviceOption != 6) {
            switch (serviceOption) {
                case 1:
                    service.register();
                    break;
                case 2:
                    service.delete();
                    break;
                case 3:
                    service.list();
                    break;
                case 4:
                    service.search();
                    break;
                case 5:
                    service.update();
            }
            serviceOption = Services.printMenu(serviceName, servicesMenuEntries);
        }
    }

}
