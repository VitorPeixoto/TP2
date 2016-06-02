package entities;

/**
 * Created by Peixoto on 24/05/2016.
 */
public class Party {
    protected String initials;

    public Party(String initials) {
        this.initials = initials;
    }

    /********************************************************************************************************************
     * Getters and Setters                                                                                              *
     ********************************************************************************************************************/

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }
}
