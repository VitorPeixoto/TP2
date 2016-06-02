package service;

import entities.Elector;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by Peixoto on 24/05/2016.
 */
public class ElectorService implements Services {
    private static ArrayList<Elector> electors;

    static {
        pao.useDelimiter(Pattern.compile("[\\n;]"));
    }

    @Override
    public void register() {
        System.out.println("ELEITOR");
        System.out.println("Nome do eleitor: ");
        String name = pao.next();
        System.out.println("Data de nascimento: ");
        String birthDateString = pao.next();
        Date newDate = new Date(brazilianToAmerican(birthDateString)); // Conversao de string em data
        System.out.println("Título: ");
        int title = pao.nextInt();
        System.out.println("Zona: ");
        int zone = pao.nextInt();
        System.out.println("Seção: ");
        int section = pao.nextInt();

        Elector e = new Elector(name,newDate,title,zone,section);

        if(verifyExistence(e)) {
            System.out.println("Título de eleitor já registrado.");
        }
        else{
            electors.add(e);
            System.out.println("Eleitor registrado com sucesso.");
        }

    }

    @Override
    public void delete() {
        System.out.println("Digite o título do eleitor: ");
        int title = pao.nextInt();

        Elector e = verifyExistence(title);
        if(e != null){
            electors.remove(e);
        }
        else{
            System.out.println("Título não encontrado.");
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

    public static boolean verifyExistence(Elector elector){
        for (Elector e : electors) {
            if(e.getTitle() == elector.getTitle()) {
                return true;
            }
        }
        return false;
    }

    public static Elector verifyExistence(int title){
        for(Elector e : electors) {
            if(e.getTitle() == title){
                return e;
            }
        }
        return null;
    }
}
