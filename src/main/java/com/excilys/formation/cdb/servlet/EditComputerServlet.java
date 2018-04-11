package com.excilys.formation.cdb.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.service.ServiceException;

/**
 * Servlet implementation class AddComputer.
 */
@WebServlet("/editComputer")
@Controller
public class EditComputerServlet extends HttpServlet {

    @Autowired
    private ComputerService computerService;
    @Autowired
    private CompanyService companyService;

    private static final long serialVersionUID = -2716486255895316442L;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        final RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/view/editComputer.jsp");
        try {
            request.setAttribute("companyList", companyService.listAllCompany());
        } catch (final ServiceException e) {
            logger.error("Can't get the company list: {}", e.getMessage(), e);
        }
        try {
            final int computerId = Integer.parseInt(request.getParameter("id"));
            final ComputerDTO computerDTO = ComputerMapper.INSTANCE
                    .computerToDTO(computerService.selectOne(computerId));
            request.setAttribute("computer", computerDTO);
        } catch (final NumberFormatException | ServiceException e1) {
            logger.info("No company selected.");
        }
        requestDispatcher.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final String idStr = request.getParameter("id");
        final String name = request.getParameter("computerName");
        final String introducedStr = request.getParameter("introduced");
        final String discontinuedStr = request.getParameter("discontinued");
        final String companyIdStr = request.getParameter("companyId");
        LocalDate introduced = null;
        LocalDate discontinued = null;
        Company manufactor = null;
        try {
            final int companyId = Integer.parseInt(companyIdStr);
            manufactor = companyService.getCompany(companyId);
        } catch (final NumberFormatException e1) {
            logger.error("No company selected.", e1);
        } catch (final ServiceException e2) {
            logger.error("Error in service that get the company.");
        }
        try {
            introduced = LocalDate.parse(introducedStr);
        } catch (final DateTimeParseException e) {
            logger.info("Introduced date was left empty.");
        }
        try {
            discontinued = LocalDate.parse(discontinuedStr);
        } catch (final DateTimeParseException e) {
            logger.info("Discontinued date was left empty.");
        }
        try {
            computerService.updateComputer(new Computer.ComputerBuilder(name).id(Integer.parseInt(idStr))
                    .dateIntroduced(introduced).dateDiscontinued(discontinued).manufactor(manufactor).build());
            logger.info("The computer has been updated to the database with success.");
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in computer adding: {}", e.getMessage(), e);
        }
        doGet(request, response);
    }
}