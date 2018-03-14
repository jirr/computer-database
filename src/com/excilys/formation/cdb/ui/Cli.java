package com.excilys.formation.cdb.ui;

import java.util.Scanner;

import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;

public class Cli {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Cli cli = new Cli();
		cli.applicationLoop();
	}
	
	public void applicationLoop () {
		boolean loop = true;
		Scanner sc = new Scanner(System.in);
		while(loop) {
			loop = listFeature(sc);
		}
		sc.close();
	}
	
	private boolean listFeature (Scanner sc) {
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
	
	private void listComputer () {
		String res = "Computers list: \n";
		res += ComputerService.INSTANCE.listComputer();
		System.out.println(res);
	}
	
	private void listCompany () {
		String res = "Companies list: \n";
		res += CompanyService.INSTANCE.listCompany();
		System.out.println(res);
	}
	
	public void selectOne (Scanner sc) {
		System.out.println("Computer Id to detail ?");
		int id = sc.nextInt();
		try {
			System.out.println(ComputerService.INSTANCE.selectOne(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createComputer (Scanner sc) {
		System.out.println("Creating computer:\n"
				+ "Computer name ?");
		String name = sc.next();
		sc.nextLine();
		System.out.println("Introduced date ? (Format: dd/MM/yyyy)");
		String introduced = sc.nextLine();
		System.out.println("Discontinued date ? (Format: dd/MM/yyyy)");
		String discontinued = sc.nextLine();
		System.out.println("Company Id ?");
		String companyId = sc.nextLine();
		try {
			System.out.println(ComputerService.INSTANCE.createComputer(name, introduced, discontinued, companyId));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void updateComputer (Scanner sc) {
		
	}
	
	private void deleteComputer (Scanner sc) {
		String saisie;
		int id = -1;
		System.out.println("Enter the computer id to delete:");
		saisie = sc.next();
		try {
			id = Integer.parseInt(saisie);
		} catch (NumberFormatException e){
			e.printStackTrace();
		}
		try {
			System.out.println(ComputerService.INSTANCE.deleteComputer(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
