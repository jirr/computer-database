package com.excilys.formation.cdb.model;

import java.time.LocalDate;

/**
 * @author jirr
 *
 */
public class Computer {
    private int id;
    private String name;
    private LocalDate dateIntroduced;
    private LocalDate dateDiscontinued;
    private Company manufactor;

    /**
     * @param id
     *            unique identifier of Computer
     * @param name
     *            name of computer
     * @param dateIntroduced
     *            the date when the computer was introduced
     * @param dateDiscontinued
     *            the date when the computer was discontinued
     * @param manufactor
     *            manufacturer of Computer
     */
    public Computer(int id, String name, LocalDate dateIntroduced, LocalDate dateDiscontinued, Company manufactor) {
        this(name, dateIntroduced, dateDiscontinued, manufactor);
        this.id = id;
    }

    /**
     * Constructor without id for creation in BDD.
     * @param name
     *            name of computer
     * @param dateIntroduced
     *            the date when the computer was introduced
     * @param dateDiscontinued
     *            the date when the computer was discontinued
     * @param manufactor
     *            manufacturer of Computer
     */
    public Computer(String name, LocalDate dateIntroduced, LocalDate dateDiscontinued, Company manufactor) {
        this.name = name;
        this.dateIntroduced = dateIntroduced;
        this.dateDiscontinued = dateDiscontinued;
        this.manufactor = manufactor;
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
     * @return the dateIntroduced
     */
    public LocalDate getDateIntroduced() {
        return dateIntroduced;
    }

    /**
     * @param dateIntroduced
     *            the dateIntroduced to set
     */
    public void setDateIntroduced(LocalDate dateIntroduced) {
        this.dateIntroduced = dateIntroduced;
    }

    /**
     * @return the dateDiscontinued
     */
    public LocalDate getDateDiscontinued() {
        return dateDiscontinued;
    }

    /**
     * @param dateDiscontinued
     *            the dateDiscontinued to set
     */
    public void setDateDiscontinued(LocalDate dateDiscontinued) {
        this.dateDiscontinued = dateDiscontinued;
    }

    /**
     * @return the idCompany
     */
    public Company getManufactor() {
        return manufactor;
    }

    /**
     * @param manufactor
     *            the idCompany to set
     */
    public void setManufactor(Company manufactor) {
        this.manufactor = manufactor;
    }

    @Override
    public String toString() {
        String result = "";
        result += "id: " + this.id;
        result += " {nom: " + this.name;
        result += ", date1: " + this.dateIntroduced;
        result += ", date2: " + this.dateDiscontinued;
        result += ", manufactor: " + this.manufactor.getName() + "}";
        return result;
    }
}
