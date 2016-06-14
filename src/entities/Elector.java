
package entities;

import java.util.Date;

/**
 * Created by Peixoto on 24/05/2016.
 */
public class Elector {
    private String name;
    private Date birthDate;
    private String title;
    private int zone;
    private int section;

    public Elector(String name, Date birthDate, String title, int zone, int section) {
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

    public String getTitle() {
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public String toString(){
        return "Eleitor "+ this.title +" : "+ this.name;
    }
}
