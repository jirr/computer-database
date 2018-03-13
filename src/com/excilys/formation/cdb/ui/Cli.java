package com.excilys.formation.cdb.ui;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.CompanyDB;
import com.excilys.formation.cdb.persistence.ComputerDB;

public class Cli {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		listFeature();
	}
	
	public static void listFeature () {
		Scanner sc = new Scanner(System.in);
		String saisie;
		boolean valid = false;
		boolean loop = true;
		
		while(!valid && loop) {
			valid = true;
			System.out.println("Chose a function:\n"
					+ "\t1) List computers\n" 
					+ "\t2) List companies\n" 
					+ "\t3) Show one computer details\n" 
					+ "\t4) Create a computer\n" 
					+ "\t5) Update a computer\n" 
					+ "\t6) Delete a computer\n"
					+ "\t7) Stop the application");
			saisie = sc.next();
			
			switch(saisie) {
				case "1":
					listComputer();
				break;
				case "2":
					listCompany();
				break;
				case "3":
					oneComputerDetails(sc);
				break;
				case "4":
					createComputer(sc);
				break;
				case "5":
					updateComputer(sc);
				break;
				case "6":
					deleteComputer(sc);
				break;
				case "7":
					loop = false;
					sc.close();
				break;
				default:
					System.out.println("Saisie invalide.");
					valid = false;
				break;
			}
		}
	}
	
	public static void listComputer () {
		ArrayList<Computer> list = ComputerDB.list();
		String res = "Computers list: \n";
		for (Computer p : list) {
			res += p.toString() + "\n";
		}
		System.out.println(res);
	}
	
	public static void listCompany () {
		ArrayList<Company> list = CompanyDB.list();
		String res = "Companies list: \n";
		for (Company c : list) {
			res += c.toString() + "\n";
		}
		System.out.println(res);
	}
	
	public static void oneComputerDetails (Scanner sc) {
		System.out.println("Computer Id to detail ?");
		int id = sc.nextInt();
		ComputerDB.selectOne(id);
	}
	
	public static void createComputer (Scanner sc) {
		System.out.println("Creating computer:\n"
				+ "Computer name ?");
		String name = sc.next();
		System.out.println("Introduced date ? (Format: dd/mm/yyyy)");
		Timestamp introduced = stringToTimestamp(sc.next());
		System.out.println("Discontinued date ? (Format: dd/mm/yyyy)");
		Timestamp discontinued = stringToTimestamp(sc.next());
		System.out.println("Company Id ?");
		int companyId = sc.nextInt();
		ComputerDB.create(new Computer(name, introduced, discontinued, companyId));
	}
	
	public static void updateComputer (Scanner sc) {
		
	}
	
	public static void deleteComputer (Scanner sc) {
		String saisie;
		System.out.println("Enter the computer id to delete:");
		saisie = sc.next();
		try {
			int id = Integer.parseInt(saisie);
			ComputerDB.delete(id);
		} catch (NumberFormatException e){
			e.printStackTrace();
		}
	}
	
	public static Timestamp stringToTimestamp(String str_date) {
	    try {
		    DateFormat format = new SimpleDateFormat("dd/mm/yyyy");
		    Date date = format.parse(str_date);
		    Timestamp timestampDate = new Timestamp(date.getTime());
		    return timestampDate;
	    } catch (ParseException e) {
	    	e.printStackTrace();
	    	return null;
	    }
	}
}
