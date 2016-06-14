package service;

import entities.Candidate;
import entities.Councilman;
import entities.Mayor;
import entities.Party;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by Peixoto on 24/05/2016.
 */
public class CandidateService implements Services {
    private static ArrayList<Candidate> candidates = new ArrayList<>();

    private final String[] candidatesMenu = {"Prefeito",
                                             "Vereador"};

    static {
        pao.useDelimiter(Pattern.compile("[\\n;]"));
    }

    @Override
    public void register() {
        int x = Services.printMenu("Candidatos", candidatesMenu);
        switch(x){
            case 1:
                System.out.println("PREFEITO");
                System.out.print("Codigo: ");
                int mayorCode = pao.nextInt();

                System.out.println("Nome: ");
                String mayorName = pao.next();

                System.out.println("E-mail: ");
                String mayorMail = pao.next();

                System.out.println("Data de Nascimento: ");
                String mayorBirth   = pao.next();
                Date mayorBirthDate = new Date(brazilianToAmerican(mayorBirth)); // Conversao de string em data

                System.out.println("Número do partido: ");
                int mayorPartyNumber = pao.nextInt(); //Verificar se o partido existe
                Party mayorParty = PartyService.returnExisting(mayorPartyNumber);
                if(mayorParty == null) {
                    System.out.println("Partido não encontrado.");
                    return;
                }

                System.out.println("VICE-PREFEITO");
                System.out.println("Codigo: ");
                int viceMayorCode = pao.nextInt();

                System.out.println("Nome: ");
                String viceMayorName = pao.next();

                System.out.println("E-mail: ");
                String viceMayorMail = pao.next();

                System.out.println("Data de Nascimento: ");
                String viceMayorBirth   = pao.next();
                Date viceMayorBirthDate = new Date(brazilianToAmerican(viceMayorBirth)); // Conversao de string em data

                System.out.println("Número do partido: ");
                int viceMayorPartyNumber = pao.nextInt(); //Verificar se o partido existe
                Party viceMayorParty          = PartyService.returnExisting(viceMayorPartyNumber);
                if(viceMayorParty == null) {
                    System.out.println("Partido não encontrado.");
                    return;
                }

                if(CandidateService.verifyExistence(mayorCode)){
                    System.out.println("Candidato já registrado.");
                }
                else{
                    Mayor m = new Mayor(mayorCode, mayorName, mayorMail, mayorBirthDate, mayorParty,
                                        viceMayorCode, viceMayorName, viceMayorMail, viceMayorBirthDate, viceMayorParty);
                    candidates.add(m);
                    System.out.println("Prefeito registrado com sucesso.");
                }
                break;
            case 2:
                System.out.println("VEREADOR");
                System.out.println("Codigo: ");
                int councilmanCode = pao.nextInt();

                System.out.println("Nome: ");
                String councilmanName = pao.next();

                System.out.println("E-mail: ");
                String councilmanMail = pao.next();

                System.out.println("Data de Nascimento: ");
                String concilmanBirth   = pao.next();
                Date councilmanBirthDate = new Date(brazilianToAmerican(concilmanBirth)); // Conversao de string em data

                System.out.println("Número do partido: ");
                int councilmanPartyNumber = pao.nextInt();
                Party councilmanParty          = PartyService.returnExisting(councilmanPartyNumber);
                if(councilmanParty == null) {
                    System.out.println("Partido não encontrado.");
                    return;
                }

                if(verifyExistence(councilmanCode)){
                    System.out.println("Vereador já registrado.");
                }
                else{
                    Councilman c = new Councilman(councilmanCode, councilmanName, councilmanMail, councilmanBirthDate, councilmanParty);
                    candidates.add(c);
                    System.out.println("Vereador registrado com sucesso.");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void delete() {
        int x = Services.printMenu("Candidatos", candidatesMenu);
        switch (x) {
            case 1:
                System.out.println("Digite o código do prefeito: ");
                int mayorCode = pao.nextInt();

                Mayor m = (Mayor)returnExisting(mayorCode);
                if (m != null) {
                    candidates.remove(m);
                    System.out.println("Prefeito deletado com sucesso.");
                } else {
                    System.out.println("Prefeito não está registrado.");
                }
                break;
            case 2:
                System.out.println("Digite o código do vereador: ");
                int councilmanCode = pao.nextInt();
                Councilman c = (Councilman) returnExisting(councilmanCode);
                if (c != null) {
                    candidates.remove(c);
                    System.out.println("Vereador deletado com sucesso.");
                } else {
                    System.out.println("Vereador não está registrado.");
                }
                break;
            default:
                break;

        }
    }

    @Override
    public void list() {
        for(Candidate c: candidates){
            if(c instanceof Mayor){
                System.out.println(((Mayor) c).toString());
            }else {
                System.out.println(((Councilman) c).toString());
            }
        }
    }

    @Override
    public void search() {
        System.out.println("Nome do candidato: ");
        String name = pao.next();

       for(Candidate c: candidates){
            if(c.getName().equals(name)){
                if(c instanceof Mayor){
                    System.out.println(((Mayor) c).toString());
                    System.out.println("Data de Nascimento: "+ c.getBirthDate());
                }else{
                    System.out.println(((Councilman) c).toString());
                    System.out.println("Data de Nascimento: "+ c.getBirthDate());
                }
            }
        }
    }

    @Override
    public void update() {
        System.out.println("Digite o código do Prefeito ou Vereador: ");
        int code = pao.nextInt();
        for(Candidate c: candidates) {
            if(c.getCode() == code) {
                System.out.println("Para cada campo, digite o novo elemento ou \"-1\" para pular. ");
                if (c instanceof Mayor) {
                    System.out.println("PREFEITO");

                    System.out.println("Nome: ");
                    String name = pao.next();
                    if(!name.equals("-1")) {
                        c.setName(name);
                    }
                    System.out.println("E-mail: ");
                    String mail = pao.next();
                    if(!mail.equals("-1")) {
                        c.setMail(mail);
                    }
                    System.out.println("Data de Nascimento: ");
                    String date = pao.next();
                    if(!date.equals("-1")) {
                        Date MayorBirthDate = new Date(brazilianToAmerican(date)); // Conversao de string em data
                        c.setBirthDate(MayorBirthDate);
                    }
                    System.out.println("Número do Partido: ");
                    int MayorPartyNumber = pao.nextInt();
                    if(MayorPartyNumber != -1) {
                        Party MayorParty = PartyService.returnExisting(MayorPartyNumber);//Verificar se o partido existe
                        if (MayorParty == null) {
                            System.out.println("Partido não encontrado. Não foi possível fazer a alteração.");
                        } else {
                            c.setParty(MayorParty);
                        }
                    }

                    System.out.println("Nome do Vice-Prefeito: ");
                    String viceName = pao.next();
                    if(!viceName.equals("-1")) {
                        ((Mayor) c).getVicemayor().setName(viceName);
                    }
                    System.out.println("E-mail do Vice-Prefeito: ");
                    String viceMail = pao.next();
                    if(!viceMail.equals("-1")) {
                        ((Mayor) c).getVicemayor().setMail(viceMail);
                    }
                    System.out.println("Data de Nascimento do Vice-Prefeito: ");
                    String viceDate = pao.next();
                    if(!viceDate.equals("-1")) {
                        Date ViceMayorBirthDate = new Date(brazilianToAmerican(viceDate)); // Conversao de string em data
                        ((Mayor) c).getVicemayor().setBirthDate(ViceMayorBirthDate);
                    }
                    System.out.println("Partido do Vice-Prefeito: ");
                    int ViceMayorPartyNumber = pao.nextInt();
                    if(ViceMayorPartyNumber != -1) {
                        Party MayorParty = PartyService.returnExisting(MayorPartyNumber);//Verificar se o partido existe
                        if (MayorParty == null) {
                            System.out.println("Partido não encontrado. Não foi possível fazer a alteração.");
                        } else {
                            ((Mayor) c).getVicemayor().setParty(MayorParty);
                        }
                    }

                }else {
                    System.out.println("VEREADOR");

                    System.out.println("Nome: ");
                    String name = pao.next();
                    if(!name.equals("-1")) {
                        c.setName(name);
                    }
                    System.out.println("E-mail: ");
                    String mail = pao.next();
                    if(!mail.equals("-1")) {
                        c.setMail(mail);
                    }
                    System.out.println("Data de Nascimento: ");
                    String date = pao.next();
                    if(!date.equals("-1")) {
                        Date CouncilmanBirthDate = new Date(brazilianToAmerican(date)); // Conversao de string em data
                        c.setBirthDate(CouncilmanBirthDate);
                    }
                    System.out.println("Partido: ");
                    int CouncilmanPartyNumber = pao.nextInt();
                    if(CouncilmanPartyNumber != -1) {
                        Party CouncilmanParty = PartyService.returnExisting(CouncilmanPartyNumber);//Verificar se o partido existe
                        if (CouncilmanParty == null) {
                            System.out.println("Partido não encontrado. Não foi possível fazer a alteração.");
                        } else {
                            c.setParty(CouncilmanParty);
                        }
                    }

                }
                System.out.println("Alteração concluída!");
            }
        }
    }

    public static boolean verifyExistence(int code){
        for (Candidate c : candidates) {
            if(c.getCode()  == code) {
                return true;
            }
        }
        return false;
    }

    public static Candidate returnExisting(int code){
        for(Candidate c : candidates) {
            if(c.getCode() == code){
                return c;
            }
        }
        return null;
    }

}
