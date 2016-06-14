import entities.Elector;
import service.CandidateService;
import service.ElectorService;
import service.PartyService;
import service.Services;

import java.security.Provider;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by Peixoto on 02/06/2016.
 */
public class Main {
    private static Scanner pao = Services.pao;
    private static CandidateService candidateService = new CandidateService();
    private static PartyService     partyService     = new PartyService();
    private static ElectorService   electorService   = new ElectorService();

    private static final String[] mainMenuEntries = new String[] {"Módulo do Administrador",
                                                                  "Módulo da Eleição",
                                                                  "Sair"};
    private static final String[] managerMenuEntries = new String[] {"Partidos",
                                                                     "Candidatos",
                                                                     "Eleitores",
                                                                     "Relatórios",
                                                                     "Menu Principal"};
    private static final String[] electionMenuEntries = new String[] {"Começar Eleição",
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
        System.out.println("Entre com os dados do presidente:");

        System.out.println("Nome do presidente: ");
        String electorName = pao.next();

        System.out.println("Data de nascimento: ");
        String electorBirth = pao.next();

        System.out.println("Título: ");
        String electorTitle = pao.next();

        System.out.println("Zona: ");
        int electorZone = pao.nextInt();

        System.out.println("Seção: ");
        int electorSection = pao.nextInt();

        electorService.registerPresident(electorName, electorBirth, electorTitle, electorZone, electorSection);
        Elector president = ElectorService.returnExistant(electorTitle);
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
