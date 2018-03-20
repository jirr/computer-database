package com.excilys.formation.cdb.model;

/**
 * @author jirr
 *
 */
public class Company {
    private int id;
    private String name;

    /**
     * @param id unique identifier of company
     * @param name Name of company
     */
    public Company(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        String result = "";
        result += "id: " + this.id;
        result += " {nom: " + this.name + "}";
        return result;
    }
}
