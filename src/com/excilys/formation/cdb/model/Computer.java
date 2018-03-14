package com.excilys.formation.cdb.model;

import java.sql.Timestamp;

public class Computer {
	private int id;
	private String name;
	private Timestamp dateIntroduced;
	private Timestamp dateDiscontinued;
	private Company manufactor;
	
	/**
	 * @param id
	 * @param name
	 * @param dateIntroduced
	 * @param dateDiscontinued
	 * @param manufacturer
	 */
	public Computer(int id, String name, Timestamp dateIntroduced, Timestamp dateDiscontinued, Company manufactor) {
		super();
		this.id = id;
		this.name = name;
		this.dateIntroduced = dateIntroduced;
		this.dateDiscontinued = dateDiscontinued;
		this.manufactor = manufactor;
	}
	
	/**
	 * Constructor without id for creation in BDD
	 * @param name
	 * @param dateIntroduced
	 * @param dateDiscontinued
	 * @param manufactor
	 */
	public Computer(String name, Timestamp dateIntroduced, Timestamp dateDiscontinued, Company manufactor) {
		super();
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
	 * @param id the id to set
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
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the dateIntroduced
	 */
	public Timestamp getDateIntroduced() {
		return dateIntroduced;
	}
	/**
	 * @param dateIntroduced the dateIntroduced to set
	 */
	public void setDateIntroduced(Timestamp dateIntroduced) {
		this.dateIntroduced = dateIntroduced;
	}
	/**
	 * @return the dateDiscontinued
	 */
	public Timestamp getDateDiscontinued() {
		return dateDiscontinued;
	}
	/**
	 * @param dateDiscontinued the dateDiscontinued to set
	 */
	public void setDateDiscontinued(Timestamp dateDiscontinued) {
		this.dateDiscontinued = dateDiscontinued;
	}

	/**
	 * @return the idCompany
	 */
	public Company getManufactor() {
		return manufactor;
	}

	/**
	 * @param manufactor the idCompany to set
	 */
	public void setManufactor(Company manufactor) {
		this.manufactor = manufactor;
	}
	
	public String toString () {
		String result = "";
		result += "id: " + this.id;
		result += " {nom: " + this.name;
		result += ", date1: " + this.dateIntroduced;
		result += ", date2: " + this.dateDiscontinued;
		result += ", manufactor: " + this.manufactor.getName() + "}";
		return result;
	}

}
