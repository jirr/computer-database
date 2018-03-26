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
import com.excilys.formation.cdb.service.ServiceException;

/**
 * Servlet implementation class AddComputer.
 */
@WebServlet("/addComputer")
public class AddComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        final RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/view/addComputer.jsp");
        try {
            request.setAttribute("companyList", CompanyService.INSTANCE.listAllCompany());
        } catch (ServiceException e) {
            logger.error("Can't get the company list: {}", e.getMessage(), e);
        }
        requestDispatcher.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String name = request.getParameter("computerName");
        final String introducedStr = request.getParameter("introduced");
        final String discontinuedStr = request.getParameter("discontinued");
        final String companyIdStr = request.getParameter("companyId");
        LocalDate introduced = null;
        LocalDate discontinued = null;
        Company manufactor = null;
        try {
            final int companyId = Integer.parseInt(companyIdStr);
            manufactor = CompanyService.INSTANCE.getCompany(companyId);
        } catch (final Exception e) {
            logger.info("No company selected.");
        }
        try {
            introduced = LocalDate.parse(introducedStr);
        } catch (final Exception e) {
            logger.info("Introduced date was left empty.");
        }
        try {
            discontinued = LocalDate.parse(discontinuedStr);
        } catch (final Exception e) {
            logger.info("Discontinued date was left empty.");
        }
        try {
            ComputerService.INSTANCE.createComputer(new Computer.ComputerBuilder(name)
                    .dateIntroduced(introduced)
                    .dateDiscontinued(discontinued)
                    .manufactor(manufactor)
                    .build());
            logger.info("The computer has been added to the database with success.");
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in computer adding: {}", e.getMessage(), e);
        }
        doGet(request, response);
    }
}
