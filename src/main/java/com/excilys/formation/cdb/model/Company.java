package com.excilys.formation.cdb.model;

/**
 * @author jirr
 *
 */
public class Company {
    private int id;
    private String name;

    /**
     * @param id
     *            unique identifier of company
     * @param name
     *            Name of company
     */
    public Company(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return String the string version of company
     */
    @Override
    public String toString() {
        String result = "";
        result += "id: " + this.id;
        result += " {nom: " + this.name + "}";
        return result;
    }
}
