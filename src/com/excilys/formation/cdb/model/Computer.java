package com.excilys.formation.cdb.model;

import java.util.Date;

public class Computer {
	private int id;
	private String name;
	private Date dateIntroduced;
	private Date dateDiscontinued;
	private int idCompany;
	
	/**
	 * @param name
	 * @param dateIntroduced
	 * @param dateDiscontinued
	 * @param manufacturer
	 */
	public Computer(int id, String name, Date dateIntroduced, Date dateDiscontinued, int idCompany) {
		super();
		this.id = id;
		this.name = name;
		this.dateIntroduced = dateIntroduced;
		this.dateDiscontinued = dateDiscontinued;
		this.idCompany = idCompany;
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
	public Date getDateIntroduced() {
		return dateIntroduced;
	}
	/**
	 * @param dateIntroduced the dateIntroduced to set
	 */
	public void setDateIntroduced(Date dateIntroduced) {
		this.dateIntroduced = dateIntroduced;
	}
	/**
	 * @return the dateDiscontinued
	 */
	public Date getDateDiscontinued() {
		return dateDiscontinued;
	}
	/**
	 * @param dateDiscontinued the dateDiscontinued to set
	 */
	public void setDateDiscontinued(Date dateDiscontinued) {
		this.dateDiscontinued = dateDiscontinued;
	}

	/**
	 * @return the idCompany
	 */
	public int getIdCompany() {
		return idCompany;
	}

	/**
	 * @param idCompany the idCompany to set
	 */
	public void setIdCompany(int idCompany) {
		this.idCompany = idCompany;
	}

}
