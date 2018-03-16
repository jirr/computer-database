package com.excilys.formation.cdb.ui;

import java.util.Scanner;

import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;

public class Cli {
	
	private int from, to;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Cli cli = new Cli();
		cli.applicationLoop();
	}
	
	private void applicationLoop () {
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
					+ "\t1) List computers\n" 				+ "\t2) List companies\n" 
					+ "\t3) Show one computer details\n" 	+ "\t4) Create a computer\n" 
					+ "\t5) Update a computer\n" 			+ "\t6) Delete a computer\n"
					+ "\t7) Stop the application");
			saisie = sc.next();
			switch(ChoiceCli.valueOf(saisie)) {
				case LIST_COMPUTER:
					listComputer(sc);
				break;
				case LIST_COMPANY:
					listCompany(sc);
				break;
				case COMPUTER_DETAIL:
					computerDetail(sc);
				break;
				case CREATE_COMPUTER:
					createComputer(sc);
				break;
				case UPDATE_COMPUTER:
					updateComputer(sc);
				break;
				case DELETE_COMPUTER:
					deleteComputer(sc);
				break;
				case STOP_APP:
					return false;
				default:
					System.err.println("Invalid choice.");
					valid = false;
				break;
			}
		}
		return true;
	}
	
	private void listComputer (Scanner sc) {
		String res = "Computers list: \n";
		boolean nextPage = true;
		from = 0;
		to = 50;
		while (nextPage) {
			res += ComputerService.INSTANCE.listComputer(from, to);
			System.out.println(res);
			nextPage = paginationChoices(sc);
		}
	}
	
	private void listCompany (Scanner sc) {
		String res = "Companies list: \n";
		boolean nextPage = true;
		from = 0;
		to = 50;
		while (nextPage) {
			try {
				res += CompanyService.INSTANCE.listCompany(from, to);
			} catch (IllegalArgumentException e) {
				System.err.println("No more entry.");
				return;
			}
			System.out.println(res);
			nextPage = paginationChoices(sc);
		}
	}
	
	private boolean paginationChoices (Scanner sc) {
		System.out.println("Next(n) Previous(p) Quit(q) ?");
		switch (sc.next()) {
			case "n" :
				from = to;
				to += 50;
			break;
			case "p" :
				to = from;
				from -= 50;
			case "q" :
				return false;
			default:
				System.err.println("Invalid choice.");
			break;
		}
		return true;
	}
	
	public void computerDetail (Scanner sc) {
		System.out.println("Computer Id to detail ?");
		int id = sc.nextInt();
		try {
			System.out.println(ComputerService.INSTANCE.selectOne(id));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	private void createComputer (Scanner sc) {
		System.out.println("Creating computer:\n"
				+ "Computer name ?");
		String name = sc.next();
		sc.nextLine();
		System.out.println("Introduced date ? (Format: yyyy-MM-dd)");
		String introduced = sc.nextLine();
		System.out.println("Discontinued date ? (Format: yyyy-MM-dd)");
		String discontinued = sc.nextLine();
		System.out.println("Company Id ?");
		String companyId = sc.nextLine();
		try {
			System.out.println(ComputerService.INSTANCE.createComputer(name, introduced, discontinued, companyId));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	private void updateComputer (Scanner sc) {
		System.out.println("Update computer:\n"
				+ "Computer ID ?");
		String id = sc.next();
		System.out.println("Company name ?");
		String name = sc.next();
		sc.nextLine();
		System.out.println("Introduced date ? (Format: yyyy-MM-dd)");
		String introduced = sc.nextLine();
		System.out.println("Discontinued date ? (Format: yyyy-MM-dd)");
		String discontinued = sc.nextLine();
		System.out.println("Company Id ?");
		String companyId = sc.nextLine();
		try {
			System.out.println(ComputerService.INSTANCE.updateComputer(id, name, introduced, discontinued, companyId));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	private void deleteComputer (Scanner sc) {
		String saisie;
		int id = -1;
		System.out.println("Enter the computer id to delete:");
		saisie = sc.next();
		try {
			id = Integer.parseInt(saisie);
		} catch (NumberFormatException e){
			System.err.println(e.getMessage());
		}
		try {
			System.out.println(ComputerService.INSTANCE.deleteComputer(id));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
