
package entities;

import java.util.Date;

/**
 * Created by Peixoto on 24/05/2016.
 */
public class Elector {
    private String name;
    private Date birthDate;
    private int title;
    private int zone;
    private int section;

    public Elector(String name, Date birthDate, int title, int zone, int section) {
        this.name = name;
        this.birthDate = birthDate;
        this.title = title;
        this.zone = zone;
        this.section = section;
    }

    /********************************************************************************************************************
     * Getters and Setters                                                                                              *
     ********************************************************************************************************************/

    public String getName() {
        return name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public int getTitle() {
        return title;
    }

    public int getZone() {
        return zone;
    }

    public int getSection() {
        return section;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    public void setSection(int section) {
        this.section = section;
    }
}
