package entities;

import service.CandidateService;
import service.ElectorService;
import service.Services;

import javax.swing.*;
import java.util.*;

/**
 * Created by Peixoto on 24/05/2016.
 */
public class Election {
    private static Scanner pao = Services.pao;
    private ElectorService electorService = ElectorService.getSingleService();
    private ArrayList<Candidate> candidates = CandidateService.getSingleService().getCandidates();

    private Elector president;
    private Date    opening,
                    ending;

    private int totalVotes[] = {0, 0};
    private int nullVotes[]  = {0, 0};
    private int whiteVotes[] = {0, 0};
    private boolean canceled = false;

    //Votos por candidato
    private HashMap<Integer, Integer> mayorVotes      = new HashMap<>();
    private HashMap<Integer, Integer> councilmanVotes = new HashMap<>();

    //Nomes por candidatos (entradas de menu)
    private HashMap<Integer, String> mayorEntries      = new HashMap<>();
    private HashMap<Integer, String> councilmanEntries = new HashMap<>();

    private ArrayList<String> alreadyVoted = new ArrayList<>();

    public Election(String presidentName, int zone, int section, String title, String birthDate, int numberOfElectors) {
        electorService.registerPresident(presidentName, birthDate, title, zone, section);
        president = electorService.returnExistant(title);
        opening   = new Date();

        initializeCollections();
        initializeVoting(numberOfElectors);
    }

    public void initializeVoting(int numberOfElectors) {
        while (totalVotes[MAYOR] < numberOfElectors){
            System.out.println("Digite seu título de eleitor: ");
            String electorTitle = pao.next();
            if(electorService.existsElectorInZoneAndSection(electorTitle, president.getZone(), president.getSection()) &&
               !alreadyVoted.contains(electorTitle)) {
                int confirmOption = 1;
                while(confirmOption != 0) {
                    int vote = Services.printMenu("Candidatos a Prefeito", mayorEntries);
                    confirmOption = JOptionPane.showConfirmDialog(null, "Deseja confirmar seu voto " + vote + "?", "Confirmação de voto", JOptionPane.YES_NO_OPTION);
                    if(confirmOption == 1) continue;
                    if (mayorVotes.keySet().contains(vote)) {
                        mayorVotes.replace(vote, mayorVotes.get(vote) + 1);
                    } else if (vote == 99) {
                        this.whiteVotes[MAYOR]++;
                    } else {
                        this.nullVotes[MAYOR]++;
                    }
                    totalVotes[MAYOR]++;
                }

                confirmOption = 1;
                while(confirmOption != 0) {
                    int vote = Services.printMenu("Candidatos a Vereador", councilmanEntries);
                    confirmOption = JOptionPane.showConfirmDialog(null, "Deseja confirmar seu voto " + vote + "?", "Confirmação de voto", JOptionPane.YES_NO_OPTION);
                    if(confirmOption == 1) continue;
                    if (councilmanVotes.keySet().contains(vote)) {
                        councilmanVotes.replace(vote, councilmanVotes.get(vote) + 1);
                    } else if (vote == 9999) {
                        this.whiteVotes[COUNCILMAN]++;
                    } else {
                        this.nullVotes[COUNCILMAN]++;
                    }
                    totalVotes[COUNCILMAN]++;
                    alreadyVoted.add(electorTitle);
                }
                System.out.println("Voto registrado (" + totalVotes[MAYOR] + "/" + numberOfElectors + ").");
            }
            else if(alreadyVoted.contains(electorTitle)){
                System.out.println("Este eleitor já votou.");
            }
            else {
                System.out.println("Este eleitor não pertence a esta Zona/Seção.");
            }
            int option = Services.printMenu("Presidente da urna", new String[]{"Continuar Votação", "Finalizar Votação", "Cancelar Votação"});
            if(option == 1) {
                if(numberOfElectors == totalVotes[MAYOR]) System.out.println("Todos os votos já foram registrados.");
            }
            else if(option == 2) {
                break;
            }
            else if(option == 3) {
                cancelVoting();
                break;
            }
        }
        ending = new Date();
        printResults();
    }

    private void printResults() {
        orderCandidatesByVotes();
        System.out.println("Resultado da eleição: ");
        System.out.println("Hora inicial: "+opening.toString());
        System.out.println("Hora final: "  +ending.toString());
        if(canceled) {
            System.out.println("A eleição foi cancelada pelo presidente da urna.");
            return;
        }
        System.out.println("Prefeitos:");
        for (Candidate c : candidates) {
            if(c instanceof Mayor) System.out.println("\tPrefeito "+c.getName()+"("+c.getCode()+"): "+mayorVotes.get(c.getCode())+";");
        }
        System.out.println("\tBrancos: "+whiteVotes[MAYOR]+";");
        System.out.println("\tNulos: "  +nullVotes[MAYOR]+";\n");

        System.out.println("Vereadores:");
        for (Candidate c : candidates) {
            if(c instanceof Councilman) System.out.println("\tVereador "+c.getName()+"("+c.getCode()+"): "+councilmanVotes.get(c.getCode())+";");
        }
        System.out.println("\tBrancos: "+whiteVotes[COUNCILMAN]+";");
        System.out.println("\tNulos: "  +nullVotes[COUNCILMAN]+";\n");

        Mayor highestVotedMayor              = null,
                secondHighestVotedMayor      = null;
        Councilman highestVotedCouncilman    = null,
                secondHighestVotedCouncilman = null;

        for (Candidate candidate : candidates) {
            if (highestVotedMayor                 == null && candidate instanceof Mayor)      highestVotedMayor            = (Mayor) candidate;
            else if (secondHighestVotedMayor      == null && candidate instanceof Mayor)      secondHighestVotedMayor      = (Mayor) candidate;
            if (highestVotedCouncilman            == null && candidate instanceof Councilman) highestVotedCouncilman       = (Councilman) candidate;
            else if (secondHighestVotedCouncilman == null && candidate instanceof Councilman) secondHighestVotedCouncilman = (Councilman) candidate;

            if (highestVotedMayor != null && highestVotedCouncilman != null && secondHighestVotedCouncilman != null && secondHighestVotedMayor != null)
                break;
        }

        //Resultado prefeitos
        if (mayorVotes.get(highestVotedMayor.getCode()) >= (Math.floor(totalVotes[MAYOR]/2)+1) ){
            System.out.println("O prefeito "+ highestVotedMayor.getName() +" venceu a eleição com "+ mayorVotes.get(highestVotedMayor.getCode()) +" votos.");
            //Exibir dados do prefeito ganhador
            for (Candidate c : candidates) {
                if(c.getCode() == highestVotedMayor.getCode()){
                    System.out.println(((Mayor) c).allData());
                    break;
                }
            }
        } else{
            System.out.println("Haverá segundo turno entre:");
            System.out.println(highestVotedMayor.allData());
            System.out.println();
            System.out.println(secondHighestVotedMayor.allData());
        }

        //Resultado vereadores
        if (councilmanVotes.get(highestVotedCouncilman.getCode()) >= (Math.floor(totalVotes[COUNCILMAN]/2)+1) ){
            System.out.println("O vereador "+ highestVotedCouncilman.getName() +" venceu a eleição com "+ councilmanVotes.get(highestVotedCouncilman.getCode()) +" votos.");
            //Exibir dados do vereador ganhador
            for (Candidate c : candidates) {
                if(c.getCode() == highestVotedCouncilman.getCode()){
                    System.out.println(((Councilman) c).allData());
                    break;
                }
            }
        } else{
            System.out.println("Haverá segundo turno entre:");
            System.out.println(highestVotedCouncilman.allData());
            System.out.println();
            System.out.println(secondHighestVotedCouncilman.allData());
        }
    }

    private void orderCandidatesByVotes() {
        Collections.sort(candidates, new Comparator<Candidate>() {
            @Override
            public int compare(Candidate o1, Candidate o2) {
                int o1Votes, o2Votes;
                if(o1 instanceof Mayor) o1Votes = mayorVotes.get(o1.getCode());
                else                    o1Votes = councilmanVotes.get(o1.getCode());
                if(o2 instanceof Mayor) o2Votes = mayorVotes.get(o2.getCode());
                else                    o2Votes = councilmanVotes.get(o2.getCode());

                return o1Votes - o2Votes;
            }
        });
        Collections.reverse(candidates);
    }

    private void cancelVoting() {
        canceled = true;
    }

    public boolean isCanceled() {
        return canceled;
    }

    private void initializeCollections() {
        for (Candidate c : candidates) {
            if(c instanceof Mayor) {
                mayorVotes.put(c.getCode(), 0);
                mayorEntries.put(c.getCode(), c.getName());
            }
            else if(c instanceof Councilman) {
                councilmanVotes.put(c.getCode(), 0);
                councilmanEntries.put(c.getCode(), c.getName());
            }
        }
    }

    public Elector getPresident() {
        return president;
    }

    public int getTotalVotes(int candidate) {
        return totalVotes[candidate];
    }

    public int[] getNullVotes() {
        return nullVotes;
    }

    public int[] getWhiteVotes() {
        return whiteVotes;
    }

    public int getMayorVotes(int code) {
        return mayorVotes.get(code);
    }

    public int getCouncilmanVotes(int code) {
        return councilmanVotes.get(code);
    }

    public static final int MAYOR      = 0,
                            COUNCILMAN = 1;
}
