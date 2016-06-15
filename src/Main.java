import entities.Candidate;
import entities.Election;
import service.CandidateService;
import service.ElectorService;
import service.PartyService;
import service.Services;

import java.util.ArrayList;
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

                    if(elections.keySet().contains(presidentSection)) {
                        System.out.println("Este presidente já apurou os votos.");
                        break;
                    }

                    Election election = new Election(presidentName, presidentZone, presidentSection, presidentTitle, presidentBirth,
                                                     electorService.getNumberOfElectorsByZoneAndSection(presidentZone, presidentSection));

                    if(election.isCanceled()) break;
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

                    reports();
                    break;
            }
        }
    }

    private static void reports() {
        int[] Votes = {0,0};
        int totalVotes = 0, totalWhiteVotes = 0, totalNullVotes = 0;
        int[] whiteVotes = {0,0};
        int[] nullVotes  = {0,0};
        int i;
        ArrayList<Candidate> candidates = candidateService.getCandidates();

        for(i=0;i<elections.size();i++) {
            Votes[0] = Votes[0] + elections.get(i).getTotalVotes(Election.MAYOR);
            Votes[1] = Votes[1] + elections.get(i).getTotalVotes(Election.COUNCILMAN);

            whiteVotes = elections.get(i).getWhiteVotes();
            totalWhiteVotes = totalWhiteVotes + whiteVotes[0] + whiteVotes[1];

            nullVotes = elections.get(i).getNullVotes();
            totalNullVotes      = totalNullVotes + nullVotes[0] + nullVotes[1];

            totalVotes = totalVotes + Votes[0] + Votes[1] + whiteVotes[0] + whiteVotes[1] + nullVotes[0] + nullVotes[1];
        }
        //@TODO FINALIZAR ULTIMO 'FOR'
        i = 0;
        for (Candidate c: candidates) {
            Votes[0] = elections.get(i).getTotalVotes(Election.MAYOR);
            //Votes[1] = elections.get(i).getTotalVotes(Election.COUNCILMAN);

            System.out.println(c.getName() +"("+ c.getCode() +") : "+ ((Votes[0]/totalVotes)*100) +"% ("+ Votes[0] +" votos)");

        }

        //Votos brancos, nulos e total de votos das eleições
        System.out.println();
        System.out.println("Votos brancos: "+ ((totalWhiteVotes/totalVotes)*100) +"% ("+ totalWhiteVotes +" votos)");
        System.out.println("Votos nulos: "+ ((totalNullVotes/totalVotes)*100) +"% ("+ totalNullVotes +" votos)");
        System.out.println("Total de Votos: "+ totalVotes + " votos");
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
