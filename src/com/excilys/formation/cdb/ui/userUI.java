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
					
				break;
				case "2":
					
				break;
				case "3":
					
				break;
				case "4":
					
				break;
				case "5":
					
				break;
				case "6":
					
				break;
				default:
					System.out.println("Saisie invalide.");
					valid = false;
				break;
			}
		}
		
	}

}
