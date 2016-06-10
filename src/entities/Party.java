package entities;

/**
 * Created by Peixoto on 24/05/2016.
 */
public class Party {
    protected final int number;
    protected String initials,
                     name;

    public Party(int number, String initials, String name) {
        this.number   = number;
        this.initials = initials;
        this.name     = name;
    }

    /********************************************************************************************************************
     * Getters and Setters                                                                                              *
     ********************************************************************************************************************/

    public String getInitials() {
        return initials;
    }

    public int getNumber() {
        return number;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return "Partido "+ this.number + " : "+ this.initials;
    }
}
