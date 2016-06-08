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
    private static ArrayList<Candidate> candidates;

    static {
        pao.useDelimiter(Pattern.compile("[\\n;]"));
    }

    @Override
    public void register() {
        System.out.println("(1)Prefeito ou (2)Vereador:");
        int x = pao.nextInt();
        switch(x){
            case 1:
                /*System.out.println("PREFEITO");
                System.out.println("Codigo: ");
                int newCode = pao.nextInt();
                System.out.println("Nome: ");
                String newName = pao.next();
                System.out.println("E-mail: ");
                String newMail = pao.next();
                System.out.println("Data de Nascimento: ");
                String nDate = pao.next();
                Date newDate = new Date(brazilianToAmerican(nDate)); // Conversao de string em data
                System.out.println("Partido: ");
                Party newParty = new Party(pao.next()); //Verificar se o partido existe
                PartyService.returnExisting(newParty);

                System.out.println("VICE-PREFEITO");
                System.out.println("Codigo: ");
                int novoCode = pao.nextInt();
                System.out.println("Nome: ");
                String novoName = pao.next();
                System.out.println("E-mail: ");
                String novoMail = pao.next();
                System.out.println("Data de Nascimento: ");
                String date = pao.next();
                Date novoDate = new Date(brazilianToAmerican(date)); // Conversao de string em data
                System.out.println("Partido: ");
                Party novoParty = new Party(pao.next()); // Verificar se o partido existe
                PartyService.returnExisting(novoParty);

                Mayor m = new Mayor(newCode,newName,newMail,newDate,newParty,novoCode,novoName,novoMail,novoDate,novoParty);

                if(verifyExistence(m)){
                    System.out.println("Código já registrado.");
                }
                else{
                    candidates.add(m);
                    System.out.println("Prefeito registrado com sucesso.");
                }*/

                break;
            case 2:
                System.out.println("VEREADOR");
                System.out.println("Codigo: ");
                int newerCode = pao.nextInt();
                System.out.println("Nome: ");
                String newerName = pao.next();
                System.out.println("E-mail: ");
                String newerMail = pao.next();
                System.out.println("Data de Nascimento: ");
                String newestDate = pao.next();
                Date newerDate = new Date(brazilianToAmerican(newestDate)); // Conversao de string em data
                System.out.println("Partido: ");
                Party newerParty = new Party(pao.next());
//              @TODO finalizar a checagem de partido
//                PartyService.returnExisting(newerParty);

                Councilman c = new Councilman(newerCode,newerName,newerMail,newerDate,newerParty);
                if(verifyExistence(newerCode)){
                    System.out.println("Código já registrado.");
                }
                else{
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
        System.out.println("(1)Prefeito ou (2)Vereador:");
        int x = pao.nextInt();
        switch (x) {
            case 1:
                System.out.println("Digite o código do prefeito: ");
                int code = pao.nextInt();
                Mayor m = (Mayor)returnExisting(code);
                if (m != null) {
                    candidates.remove(m);
                    System.out.println("Prefeito deletado com sucesso.");
                } else {
                    System.out.println("Código não está registrado.");
                }
                break;

            case 2:
                System.out.println("Digite o código do prefeito: ");
                int newcode = pao.nextInt();
                Councilman c = (Councilman) returnExisting(newcode);
                if (c != null) {
                    candidates.remove(c);
                    System.out.println("Vereador deletado com sucesso.");
                } else {
                    System.out.println("Código não está registrado.");
                }
                break;
            default:
                break;

        }
    }
    @Override
    public void list() {

    }

    @Override
    public void search() {

    }

    @Override
    public void update() {

    }

    public static boolean verifyExistence(int code){
        for (Candidate c : candidates) {
            if(c.getCode() == code) {
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
