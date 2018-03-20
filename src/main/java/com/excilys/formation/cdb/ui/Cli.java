package com.excilys.formation.cdb.ui;

import java.time.LocalDate;
import java.util.Scanner;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.service.CompanyPage;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerPage;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.service.Page;

public class Cli {
    private static final int PAGE_SIZE = 50;

    /**
     * @param args the arguments
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Cli cli = new Cli();
        cli.applicationLoop();
    }

    /**
     *
     */
    private void applicationLoop() {
        boolean loop = true;
        Scanner scanner = new Scanner(System.in);
        while (loop) {
            loop = listFeature(scanner);
        }
        scanner.close();
    }

    /**
     * @param scanner The scanner of the CLI
     * @return boolean false to stop the loop
     */
    private boolean listFeature(Scanner scanner) {
        String saisie;
        boolean valid = false;
        while (!valid) {
            valid = true;
            System.out.println("Chose a function:\n"
                    + "\t1) List computers\n"
                    + "\t2) List companies\n"
                    + "\t3) Show one computer details\n"
                    + "\t4) Create a computer\n"
                    + "\t5) Update a computer\n"
                    + "\t6) Delete a computer\n"
                    + "\t7) Stop the application");
            saisie = scanner.next();
            switch (ActionChoiceCli.getById(saisie)) {
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

    /**
     * @param scanner The scanner of the CLI
     */
    private void listComputer(Scanner scanner) {
        System.out.println("Computers list: \n");
        ComputerPage page = new ComputerPage(PAGE_SIZE);
        paginationChoices(scanner, page);
    }

    /**
     * @param scanner The scanner of the CLI
     */
    private void listCompany(Scanner scanner) {
        System.out.println("Companies list: \n");
        CompanyPage page = new CompanyPage(PAGE_SIZE);
        paginationChoices(scanner, page);
    }

    /**
     * @param scanner The scanner of the CLI
     * @param page The computer or company Page
     * @param <T> Computer or Company
     */
    private <T extends Page<?>> void paginationChoices(Scanner scanner, final T page) {
        boolean nextPage = true;
        page.getContent().forEach(System.out::println);;
        while (nextPage) {
            System.out.println("First(f) Next(n) Previous(p) Last(l) Quit(q) ?");
            switch (PageChoiceCli.getById(scanner.next())) {
            case NEXT_PAGE:
                page.nextPage().forEach(System.out::println);;
                break;
            case PREVIOUS_PAGE:
                page.previousPage().forEach(System.out::println);;
            case FIRST_PAGE:
                page.firstPage().forEach(System.out::println);;
                break;
            case LAST_PAGE:
                page.lastPage().forEach(System.out::println);;
                break;
            case QUIT_PAGE:
                nextPage = false;
                break;
            case DEFAULT:
                System.err.println("Invalid choice.");
                break;
            }
        }
    }

    /**
     * @param scanner The scanner of the CLI
     */
    public void computerDetail(Scanner scanner) {
        System.out.println("Computer Id to detail ?");
        int id = scanner.nextInt();
        try {
            System.out.println(ComputerService.INSTANCE.selectOne(id));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * @param scanner The scanner of the CLI
     */
    private void createComputer(Scanner scanner) {
        System.out.println("Creating computer:\n" + "Computer name ?");
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
            System.out.println(
                    ComputerService.INSTANCE.createComputer(new Computer(name, introduced, discontinued, manufactor)));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * @param scanner The scanner of the CLI
     */
    private void updateComputer(Scanner scanner) {
        System.out.println("Update computer:\n" + "Computer ID ?");
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
            System.out.println(ComputerService.INSTANCE
                    .updateComputer(new Computer(id, name, introduced, discontinued, manufactor)));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * @param scanner The scanner of the CLI
     */
    private void deleteComputer(Scanner scanner) {
        String saisie;
        System.out.println("Enter the computer id to delete:");
        saisie = scanner.next();
        try {
            int id = Integer.parseInt(saisie);
            System.out.println(ComputerService.INSTANCE.deleteComputer(id));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}