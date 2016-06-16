import entities.Candidate;
import entities.Councilman;
import entities.Election;
import entities.Mayor;
import graphics.BallotBoxFrame;
import service.CandidateService;
import service.ElectorService;
import service.PartyService;
import service.Services;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
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
                case 3:
                    return;
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
                    election.close();
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
                    reports();
                    break;
            }
        }
    }

    private static HashMap<Integer, Integer> sumElectionHashMaps(HashMap<Integer, Integer> map1, HashMap<Integer, Integer> map2) {
        HashMap<Integer, Integer> map3 = new HashMap<>();
        for (Integer key : map1.keySet()) {
            map3.put(key, map1.get(key)+map2.get(key));
        }
        return map3;
    }

    private static int[] sumElectionArrays(int[] array1, int[] array2) {
        int[] array3 = new int[array1.length];
        for (int i = 0; i < array1.length; i++) {
            array3[i] = array1[i] + array2[i];
        }
        return array3;
    }

    private static void reports() {
        HashMap<Integer, Integer> finalMayorVotes      = null;
        HashMap<Integer, Integer> finalCouncilmanVotes = null;

        int[] totalVotes      = new int[2];
        int[] totalWhiteVotes = new int[2];
        int[] totalNullVotes  = new int[2];
        for (Integer section : elections.keySet()) {
            if(finalMayorVotes == null) {
                finalMayorVotes      = elections.get(section).getMayorVotes();
                finalCouncilmanVotes = elections.get(section).getCouncilmanVotes();
                totalVotes           = elections.get(section).getTotalVotes();
                totalWhiteVotes      = elections.get(section).getWhiteVotes();
                totalNullVotes       = elections.get(section).getNullVotes();
            }
            else {
                finalMayorVotes      = sumElectionHashMaps(finalMayorVotes,      elections.get(section).getMayorVotes());
                finalCouncilmanVotes = sumElectionHashMaps(finalCouncilmanVotes, elections.get(section).getCouncilmanVotes());

                totalVotes           = sumElectionArrays(totalVotes,      elections.get(section).getTotalVotes());
                totalWhiteVotes      = sumElectionArrays(totalWhiteVotes, elections.get(section).getWhiteVotes());
                totalNullVotes       = sumElectionArrays(totalNullVotes,  elections.get(section).getNullVotes());
            }
        }

        CandidateService.getSingleService().orderCandidatesByVotes(finalMayorVotes, finalCouncilmanVotes);
        ArrayList<Candidate> candidates = CandidateService.getSingleService().getCandidates();
        //Prefeito
        System.out.println("Resultado da eleição para prefeito: ");
        for (Candidate c : candidates) {
            if(c instanceof Mayor) System.out.println("\t"+c.getName() +" ("+ c.getCode() +") : "+ ((finalMayorVotes.get(c.getCode())/(float)totalVotes[MAYOR])*100) +"% ("+ finalMayorVotes.get(c.getCode()) +" votos);");
        }

        //Votos brancos, nulos e total de votos das eleições
        System.out.println();
        System.out.println("Votos brancos: " + ((totalWhiteVotes[MAYOR]/(float)totalVotes[MAYOR])*100) +"% ("+ totalWhiteVotes[MAYOR] +" votos);");
        System.out.println("Votos nulos: "   + ((totalNullVotes[MAYOR] /(float)totalVotes[MAYOR])*100) +"% ("+ totalNullVotes[MAYOR]  +" votos);");
        System.out.println("Total de Votos: "+ totalVotes[MAYOR] + " votos;");


        //Vereador
        System.out.println("Resultado da eleição para vereador: ");
        for (Candidate c : candidates) {
            if(c instanceof Councilman) System.out.println("\t"+c.getName() +" ("+ c.getCode() +") : "+ ((finalCouncilmanVotes.get(c.getCode())/(float)totalVotes[COUNCILMAN])*100) +"% ("+ finalCouncilmanVotes.get(c.getCode()) +" votos);");
        }

        //Votos brancos, nulos e total de votos das eleições
        System.out.println();
        System.out.println("Votos brancos: " + ((totalWhiteVotes[COUNCILMAN]/(float)totalVotes[COUNCILMAN])*100) +"% ("+ totalWhiteVotes[COUNCILMAN] +" votos);");
        System.out.println("Votos nulos: "   + ((totalNullVotes[COUNCILMAN] /(float)totalVotes[COUNCILMAN])*100) +"% ("+ totalNullVotes[COUNCILMAN]  +" votos);");
        System.out.println("Total de Votos: "+ totalVotes[COUNCILMAN] + " votos;");
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
    public static final int MAYOR      = 0,
                            COUNCILMAN = 1;
}
