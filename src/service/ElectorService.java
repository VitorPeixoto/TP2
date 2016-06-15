package service;

import entities.Candidate;
import entities.Elector;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by Peixoto on 24/05/2016.
 */
public class ElectorService implements Services {
    private static ElectorService singleService = null;

    private ArrayList<Elector> electors;

    private ElectorService() {
        electors = new ArrayList<>();
    }

    static {
        pao.useDelimiter(Pattern.compile("[\\n;]"));
    }

    public static ElectorService getSingleService() {
        if(singleService == null) {
            singleService = new ElectorService();
        }
        return singleService;
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

    public void registerPresident(String electorName, String electorBirth, String electorTitle, int electorZone, int electorSection) {
        Date electorBirthDate = new Date(brazilianToAmerican(electorBirth)); // Conversao de string em data
        if(verifyExistence(electorTitle)) {
            System.out.println("Título de eleitor já registrado.");
        }
        else{
            Elector e = new Elector(electorName, electorBirthDate, electorTitle, electorZone, electorSection);
            electors.add(e);
            System.out.println("Presidente registrado com sucesso.");
        }
    }

    @Override
    public void delete() {
        System.out.println("Digite o título do eleitor: ");
        String title = pao.next();

        Elector e = returnExistant(title);
        if(e != null){
            electors.remove(e);
            System.out.println("Eleitor excluído!");
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

        for(Elector e : electors){
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
                System.out.println("Alteração concluída!");
                break;
            }
        }

        System.out.println("Alteração concluída!");
    }

    public int getNumberOfElectorsByZoneAndSection(int zone, int section) {
        int numberOfElectors = 0;
        for (Elector e : electors) {
            if (e.getSection() == section && e.getZone() == zone) numberOfElectors++;
        }
        return numberOfElectors;
    }

    public boolean existsElectorInZoneAndSection(String title, int zone, int section) {
        for (Elector e : electors) {
            if (e.getSection() == section && e.getZone() == zone && e.getTitle().equals(title)) return true;
        }
        return false;
    }

    public ArrayList<Elector> getElectors() {
        return electors;
    }

    public boolean verifyExistence(String title){
        for (Elector e : electors) {
            if(e.getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }

    public Elector returnExistant(String title){
        for(Elector e : electors) {
            if(e.getTitle().equals(title)){
                return e;
            }
        }
        return null;
    }
}
