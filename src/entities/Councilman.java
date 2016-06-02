package entities;

import java.util.Date;

/**
 * Created by Peixoto on 24/05/2016.
 */
public class Councilman extends Candidate {

    public Councilman(int code, String name, String mail, Date birthDate, Party party) {
        super(code, name, mail, birthDate, party);
    }

}
