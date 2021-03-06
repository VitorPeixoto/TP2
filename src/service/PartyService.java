package service;

import entities.Candidate;
import entities.Party;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by Peixoto on 24/05/2016.
 */
public class PartyService implements Services {
    private static PartyService singleService = null;

    private ArrayList<Party> parties;

    private PartyService() {
        parties = new ArrayList<>();
    }

    static {
        pao.useDelimiter(Pattern.compile("[\\n;]"));
    }

    public static PartyService getSingleService() {
        if(singleService == null) {
            singleService = new PartyService();
        }
        return singleService;
    }

    @Override
    public void register() {
        System.out.println("PARTIDO");

        System.out.println("Número do partido: ");
        int partyNumber = pao.nextInt();

        System.out.println("Sigla do partido: ");
        String partyInitials = pao.next();

        System.out.println("Nome do Partido: ");
        String partyName = pao.next();

        if(verifyExistence(partyNumber)) {
            System.out.println("Partido já registrado.");
        }
        else{
            parties.add(new Party(partyNumber, partyInitials, partyName));
            System.out.println("Partido realizado com sucesso!");
        }
    }

    @Override
    public void delete() {
        System.out.println("Digite o número do partido: ");
        int number = pao.nextInt();
        Party p = returnExisting(number);

        if(p != null) {
            parties.remove(p);
            System.out.println("Partido excluído!");
        }
        else {
            System.out.println("Partido não registrado.");
        }
    }

    @Override
    public void list() {
        for(Party p: parties){
            System.out.println(p.toString());
        }
    }

    @Override
    public void search() {
        System.out.println("Digite o número do Partido: ");
        int number = pao.nextInt();
        for(Party p : parties){
            if(p.getNumber() == number){
                System.out.println(p.toString());
            }
        }
    }

    @Override
    public void update() {
        System.out.println("Digite o número do Partido: ");
        int number = pao.nextInt();
        for(Party p : parties){
            if(p.getNumber() == number){
                System.out.println("Para cada campo, digite o novo elemento ou \"-1\" para pular. ");
                System.out.println("Sigla: ");
                String initials = pao.next();
                if(!initials.equals("-1")){
                    p.setInitials(initials);
                }
                System.out.println("Nome: ");
                String name = pao.next();
                if(!name.equals("-1")){
                    p.setName(name);
                }
                System.out.println("Alteração concluída!");
            }
        }
    }

    public ArrayList<Party> getParties() {
        return parties;
    }

    public boolean verifyExistence(int number){
        for (Party p : parties) {
            if(p.getNumber() == number) {
                return true;
            }
        }
        return false;
    }

    public Party returnExisting(int number){
        for(Party p : parties){
            if(p.getNumber() == number){
                return p;
            }
        }
        return null;
    }
}
