package service;

import entities.Elector;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by Peixoto on 24/05/2016.
 */
public class ElectorService implements Services {
    private static ArrayList<Elector> electors = new ArrayList<>();

    static {
        pao.useDelimiter(Pattern.compile("[\\n;]"));
    }

    @Override
    public void register() {
        System.out.println("ELEITOR");
        System.out.println("Nome do eleitor: ");
        String electorName = pao.next();

        System.out.println("Data de nascimento: ");
        String electorBirth = pao.next();
        Date electorBirthDate = new Date(brazilianToAmerican(electorBirth)); // Conversao de string em data

        System.out.println("Título: ");
        String electorTitle = pao.next();

        System.out.println("Zona: ");
        int electorZone = pao.nextInt();

        System.out.println("Seção: ");
        int electorSection = pao.nextInt();

        if(verifyExistence(electorTitle)) {
            System.out.println("Título de eleitor já registrado.");
        }
        else{
            Elector e = new Elector(electorName, electorBirthDate, electorTitle, electorZone, electorSection);
            electors.add(e);
            System.out.println("Eleitor registrado com sucesso.");
        }

    }

    @Override
    public void delete() {
        System.out.println("Digite o título do eleitor: ");
        String title = pao.next();

        Elector e = returnExistant(title);
        if(e != null){
            electors.remove(e);
        }
        else{
            System.out.println("Eleitor não encontrado.");
        }
    }

    @Override
    public void list() {
        for(Elector e: electors){
            System.out.println(e.toString());
        }
    }

    @Override
    public void search() {
        System.out.println("Digite o nome do eleitor: ");
        String name = pao.next();

        for(Elector e: electors){
            if(e.getName().equals(name)){
                System.out.println(e.toString());
                System.out.println("Data de Nascimento: "+ e.getBirthDate());
            }
        }
    }

    @Override
    public void update() {
        System.out.println("Digite o número do Título do eleitor: ");
        String title = pao.next();

        for(Elector e: electors){
            if(e.getTitle().equals(title)){
                System.out.println("Para cada campo, digite o novo elemento ou \"-1\" para pular. ");
                System.out.println("Nome: ");
                String name = pao.next();
                if(!name.equals("-1")){
                    e.setName(name);
                }
                System.out.println("Data de Nascimento: ");
                String electorDate = pao.next();
                if(!electorDate.equals("-1")){
                    Date electorBirthDate = new Date(brazilianToAmerican(electorDate)); // Conversao de string em data
                    e.setBirthDate(electorBirthDate);
                }
                System.out.println("Zona: ");
                int zone = pao.nextInt();
                if(zone != -1){
                    e.setZone(zone);
                }
                System.out.println("Seção: ");
                int section = pao.nextInt();
                if(section != -1){
                    e.setSection(section);
                }
                break;
            }
        }

        System.out.println("Alteração concluída!");
    }

    public static boolean verifyExistence(String title){
        for (Elector e : electors) {
            if(e.getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }

    public static Elector returnExistant(String title){
        for(Elector e : electors) {
            if(e.getTitle().equals(title)){
                return e;
            }
        }
        return null;
    }
}
