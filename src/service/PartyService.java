package service;

import entities.Party;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by Peixoto on 24/05/2016.
 */
public class PartyService implements Services {
    private static ArrayList<Party> parties;

    static {
        pao.useDelimiter(Pattern.compile("[\\n;]"));
    }

    @Override
    public void register() {
        System.out.println("PARTIDO");
        System.out.println("Sigla do partido: ");
        String initials = pao.next();

        if(verifyExistence(initials)) {
            System.out.println("Partido já registrado.");
        }
        else{
            parties.add(new Party(initials));
        }
    }

    @Override
    public void delete() {
        System.out.println("Digite a sigla do partido: ");
        String initials = pao.next();
        Party p = returnExisting(initials);

        if(p != null) {
            parties.remove(p);
        }
        else {
            System.out.println("Partido não registrado.");
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

    public static boolean verifyExistence(String initials){
        for (Party p : parties) {
            if(p.getInitials().equals(initials)) {
                return true;
            }
        }
        return false;
    }

    public static Party returnExisting(String initials){
        for(Party p : parties){
            if(p.getInitials().equals(initials)){
                return p;
            }
        }
        return null;
    }
}
