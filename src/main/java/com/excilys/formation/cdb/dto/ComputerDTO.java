package com.excilys.formation.cdb.dto;

/**
 * @author jirr
 *
 */
public class ComputerDTO {
    private int id;
    private String name;
    private String dateIntroduced;
    private String dateDiscontinued;
    private String manufactor;

    /**
     * @param id unique identifier of Computer
     * @param name name of computer
     * @param dateIntroduced the date when the computer was introduced
     * @param dateDiscontinued the date when the computer was discontinued
     * @param manufactor manufacturer of Computer
     */
    public ComputerDTO(int id, String name, String dateIntroduced, String dateDiscontinued, String manufactor) {
        this(name, dateIntroduced, dateDiscontinued, manufactor);
        this.id = id;
    }

    /**
     * Constructor without id for creation in BDD.
     * @param name name of computer
     * @param dateIntroduced the date when the computer was introduced
     * @param dateDiscontinued the date when the computer was discontinued
     * @param manufactor manufacturer of Computer
     */
    public ComputerDTO(String name, String dateIntroduced, String dateDiscontinued, String manufactor) {
        this.name = name;
        this.dateIntroduced = dateIntroduced;
        this.dateDiscontinued = dateDiscontinued;
        this.manufactor = manufactor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateIntroduced() {
        return dateIntroduced;
    }

    public void setDateIntroduced(String dateIntroduced) {
        this.dateIntroduced = dateIntroduced;
    }

    public String getDateDiscontinued() {
        return dateDiscontinued;
    }

    public void setDateDiscontinued(String dateDiscontinued) {
        this.dateDiscontinued = dateDiscontinued;
    }

    public String getManufactor() {
        return manufactor;
    }

    public void setManufactor(String manufactor) {
        this.manufactor = manufactor;
    }

    @Override
    public String toString() {
        String result = "";
        result += "id: " + this.id;
        result += " {nom: " + this.name;
        result += ", date1: " + this.dateIntroduced;
        result += ", date2: " + this.dateDiscontinued;
        result += ", manufactor: " + this.manufactor + "}";
        return result;
    }
}
