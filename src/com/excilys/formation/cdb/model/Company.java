package com.excilys.formation.cdb.model;

public class Company {
	private int id;
	private String name;

	/**
	 * @param name
	 */
	public Company(String name) {
		super();
		this.name = name;
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
}
