package com.excilys.formation.cdb.ui;

import java.time.LocalDate;
import java.util.Scanner;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
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
		Scanner scanner = new Scanner(System.in);
		while(loop) {
			loop = listFeature(scanner);
		}
		scanner.close();
	}
	
	private boolean listFeature (Scanner scanner) {
		String saisie;
		boolean valid = false;
		
		while(!valid) {
			valid = true;
			System.out.println("Chose a function:\n"
					+ "\t1) List computers\n" 				+ "\t2) List companies\n" 
					+ "\t3) Show one computer details\n" 	+ "\t4) Create a computer\n" 
					+ "\t5) Update a computer\n" 			+ "\t6) Delete a computer\n"
					+ "\t7) Stop the application");
			saisie = scanner.next();
			switch(ChoiceCli.getById(saisie)) {
				case LIST_COMPUTER:
					listComputer(scanner);
				break;
				case LIST_COMPANY:
					listCompany(scanner);
				break;
				case COMPUTER_DETAIL:
					computerDetail(scanner);
				break;
				case CREATE_COMPUTER:
					createComputer(scanner);
				break;
				case UPDATE_COMPUTER:
					updateComputer(scanner);
				break;
				case DELETE_COMPUTER:
					deleteComputer(scanner);
				break;
				case STOP_APP:
					return false;
				case DEFAULT:
					System.err.println("Invalid choice.");
					valid = false;
				break;
			}
		}
		return true;
	}
	
	private void listComputer (Scanner scanner) {
		String res = "Computers list: \n";
		boolean nextPage = true;
		from = 0;
		to = 50;
		while (nextPage) {
			res += ComputerService.INSTANCE.listComputer(from, to);
			System.out.println(res);
			nextPage = paginationChoices(scanner);
		}
	}
	
	private void listCompany (Scanner scanner) {
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
			nextPage = paginationChoices(scanner);
		}
	}
	
	private boolean paginationChoices (Scanner scanner) {
		System.out.println("Next(n) Previous(p) Quit(q) ?");
		switch (scanner.next()) {
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
	
	public void computerDetail (Scanner scanner) {
		System.out.println("Computer Id to detail ?");
		int id = scanner.nextInt();
		try {
			System.out.println(ComputerService.INSTANCE.selectOne(id));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	private void createComputer (Scanner scanner) {
		System.out.println("Creating computer:\n"
				+ "Computer name ?");
		String name = scanner.next();
		scanner.nextLine();
		System.out.println("Introduced date ? (Format: yyyy-MM-dd)");
		String introducedStr = scanner.nextLine();
		System.out.println("Discontinued date ? (Format: yyyy-MM-dd)");
		String discontinuedStr = scanner.nextLine();
		System.out.println("Company Id ?");
		String companyIdStr = scanner.nextLine();
		try {
			int companyId = Integer.parseInt(companyIdStr);
			LocalDate introduced = LocalDate.parse(introducedStr);
			LocalDate discontinued = LocalDate.parse(discontinuedStr);
			Company manufactor = CompanyService.INSTANCE.getCompany(companyId);
			System.out.println(ComputerService.INSTANCE.createComputer(new Computer(name, introduced, discontinued, manufactor)));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	private void updateComputer (Scanner scanner) {
		System.out.println("Update computer:\n"
				+ "Computer ID ?");
		String idStr = scanner.next();
		System.out.println("Company name ?");
		String name = scanner.next();
		scanner.nextLine();
		System.out.println("Introduced date ? (Format: yyyy-MM-dd)");
		String introducedStr = scanner.nextLine();
		System.out.println("Discontinued date ? (Format: yyyy-MM-dd)");
		String discontinuedStr = scanner.nextLine();
		System.out.println("Company Id ?");
		String companyIdStr = scanner.nextLine();
		try {
			int id = Integer.parseInt(idStr);
			int companyId = Integer.parseInt(companyIdStr);
			LocalDate introduced = LocalDate.parse(introducedStr);
			LocalDate discontinued = LocalDate.parse(discontinuedStr);
			Company manufactor = CompanyService.INSTANCE.getCompany(companyId);
			System.out.println(ComputerService.INSTANCE.updateComputer(new Computer(id, name, introduced, discontinued, manufactor)));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	private void deleteComputer (Scanner scanner) {
		String saisie;
		int id = -1;
		System.out.println("Enter the computer id to delete:");
		saisie = scanner.next();
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
