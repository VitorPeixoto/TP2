package entities;

import java.util.Date;

/**
 * Created by Peixoto on 24/05/2016.
 */
public abstract class Candidate  {
    private int code;
    private String name,
                   mail;
    private Date birthDate;
    private Party party;

    public Candidate(int code, String name, String mail, Date birthDate, Party party) {
        this.code = code;
        this.name = name;
        this.mail = mail;
        this.birthDate = birthDate;
        this.party = party;
    }

   /********************************************************************************************************************
    * Getters and Setters                                                                                              *
    ********************************************************************************************************************/
    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Party getParty() {
        return party;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setParty(Party party) {
        this.party = party;
    }
}