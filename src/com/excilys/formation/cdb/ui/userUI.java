package com.excilys.formation.cdb.ui;

import java.util.Scanner;

public class userUI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		String saisie;
		boolean valid = false;
		
		while(!valid) {
			valid = true;
			System.out.println("Choisir une action :\n"
					+ "\t1) List computers\n" 
					+ "\t2) List companies\n" 
					+ "\t3) Show one computer details\n" 
					+ "\t4) Create a computer\n" 
					+ "\t5) Update a computer\n" 
					+ "\t6) Delete a computer");
			saisie = sc.next();
			
			switch(saisie) {
				case "1":
					listComputer();
				break;
				case "2":
					listCompany();
				break;
				case "3":
					oneComputerDetails();
				break;
				case "4":
					createComputer();
				break;
				case "5":
					updateComputer();
				break;
				case "6":
					deleteComputer();
				break;
				default:
					System.out.println("Saisie invalide.");
					valid = false;
				break;
			}
		}
		
	}
	
	public static void listComputer () {
		
	}
	
	public static void listCompany () {
		
	}
	
	public static void oneComputerDetails () {
		
	}
	
	public static void createComputer () {
		
	}
	
	public static void updateComputer () {
		
	}
	
	public static void deleteComputer () {
		
	}

}
