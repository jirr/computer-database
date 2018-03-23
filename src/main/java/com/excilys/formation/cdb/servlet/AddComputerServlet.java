package com.excilys.formation.cdb.servlet;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet("/addComputer")
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/view/addComputer.jsp");
        request.setAttribute("companyList", CompanyService.INSTANCE.listAllCompany());

        requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("computerName");
        String introducedStr = request.getParameter("introduced");
        String discontinuedStr = request.getParameter("discontinued");
        String companyIdStr = request.getParameter("companyId");
        LocalDate introduced = null;
        LocalDate discontinued = null;
        Company manufactor = null;
        try {
            int companyId = Integer.parseInt(companyIdStr);
            manufactor = CompanyService.INSTANCE.getCompany(companyId);
            introduced = LocalDate.parse(introducedStr);
            discontinued = LocalDate.parse(discontinuedStr);
        } catch (Exception e) {
            logger.info("Fields were left empty: {}",e.getMessage(), e);
        }
        try {
            ComputerService.INSTANCE.createComputer(new Computer.ComputerBuilder(name)
                                                                .dateIntroduced(introduced)
                                                                .dateDiscontinued(discontinued)
                                                                .manufactor(manufactor)
                                                                .build());
            logger.info("The computer has been added to the database with success.");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in computer adding: {}",e.getMessage(), e);
        }
        doGet(request, response);
	}
}
