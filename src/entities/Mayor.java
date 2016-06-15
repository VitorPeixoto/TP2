package entities;

import java.util.Date;

/**
 * Created by Peixoto on 24/05/2016.
 */
public class Mayor extends Candidate{

    private ViceMayor vicemayor;

    public Mayor(int code, String name, String mail, Date birthDate, Party party,
                 int codev, String namev, String mailv, Date birthDatev, Party partyv) {
        super(code, name, mail, birthDate, party);
        this.vicemayor = new ViceMayor(codev, namev,mailv,birthDatev,partyv);

    }

    /********************************************************************************************************************
     * Getters and Setters                                                                                              *
     ********************************************************************************************************************/

    public ViceMayor getVicemayor() {
        return vicemayor;
    }

    public void setVicemayor(ViceMayor vicemayor) {
        this.vicemayor = vicemayor;
    }

    public String toString(){
        return "Prefeito "+ getCode() +" : "+ getName();
    }

    public String allData(){
        return "Prefeito: \n"+ super.toString() +"\nVice-Prefeito: "+ vicemayor.getName();
    }

}
