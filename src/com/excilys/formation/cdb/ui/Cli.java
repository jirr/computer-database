package com.excilys.formation.cdb.ui;

import java.util.Scanner;

import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;

public class Cli {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		applicationLoop();
	}
	
	public static void applicationLoop () {
		boolean loop = true;
		Scanner sc = new Scanner(System.in);
		while(loop) {
			loop = listFeature(sc);
		}
		sc.close();
	}
	
	public static boolean listFeature (Scanner sc) {
		String saisie;
		boolean valid = false;
		
		while(!valid) {
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
					selectOne(sc);
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
					return false;
				default:
					System.out.println("Saisie invalide.");
					valid = false;
				break;
			}
		}
		return true;
	}
	
	public static void listComputer () {
		String res = "Computers list: \n";
		res += ComputerService.listComputer();
		System.out.println(res);
	}
	
	public static void listCompany () {
		String res = "Companies list: \n";
		res += CompanyService.listCompany();
		System.out.println(res);
	}
	
	public static void selectOne (Scanner sc) {
		System.out.println("Computer Id to detail ?");
		int id = sc.nextInt();
		System.out.println(ComputerService.selectOne(id));
	}
	
	public static void createComputer (Scanner sc) {
		System.out.println("Creating computer:\n"
				+ "Computer name ?");
		String name = sc.next();
		System.out.println("Introduced date ? (Format: dd/mm/yyyy)");
		String introduced = sc.next();
		System.out.println("Discontinued date ? (Format: dd/mm/yyyy)");
		String discontinued = sc.next();
		System.out.println("Company Id ?");
		int companyId = sc.nextInt();
		System.out.println(ComputerService.createComputer(name, introduced, discontinued, companyId));
	}
	
	public static void updateComputer (Scanner sc) {
		
	}
	
	public static void deleteComputer (Scanner sc) {
		String saisie;
		int id = -1;
		System.out.println("Enter the computer id to delete:");
		saisie = sc.next();
		try {
			id = Integer.parseInt(saisie);
		} catch (NumberFormatException e){
			e.printStackTrace();
		}
		System.out.println(ComputerService.deleteComputer(id));
	}
}
