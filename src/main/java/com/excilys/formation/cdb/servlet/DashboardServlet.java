package com.excilys.formation.cdb.servlet;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

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
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.service.ServiceException;
import com.excilys.formation.cdb.service.pagination.ComputerPage;

@WebServlet("/dashboard.old")
@Controller
public class DashboardServlet extends HttpServlet {

    @Autowired
    private ComputerService computerService;
    @Autowired
    private ComputerMapper computerMapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private static final long serialVersionUID = 2741128895945909738L;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int nbComputer = -1;
        ComputerPage page = null;
        try {
            page = new ComputerPage(10, computerService);
            if (!(request.getParameter("search") == null)) {
                page.setKeywords(request.getParameter("search"));
            }
            if (!(request.getParameter("size") == null)) {
                try {
                    int size = Integer.parseInt(request.getParameter("size"));
                    page.setSize(size);
                } catch (NumberFormatException e1) {
                    logger.error("Not a number(size): {}", e1.getMessage());
                }
            }
            if (!(request.getParameter("index") == null)) {
                try {
                    int index = Integer.parseInt(request.getParameter("index"));
                    page.goToPage(index - 1);
                } catch (NumberFormatException e1) {
                    logger.error("Not a number(index): {}", e1.getMessage());
                }
            }
            if (!(request.getParameter("sort") == null)) {
                page.setSortBy(request.getParameter("sort"));
            }
            if (!(request.getParameter("asc") == null)) {
                page.setAsc(Boolean.parseBoolean(request.getParameter("asc")));
            }
            nbComputer = computerService.countAllComputers(page.getKeywords());
        } catch (ServiceException e3) {
            logger.error("Error in Service execution: {}", e3.getMessage(), e3);
        }
        List<ComputerDTO> computersDTO = new ArrayList<>();
        page.getContent().forEach(computer -> computersDTO.add(computerMapper.computerToDTO(computer)));
        request.setAttribute("nbComputers", nbComputer);
        request.setAttribute("computer_list", computersDTO);
        request.setAttribute("keywords", page.getKeywords());
        request.setAttribute("maxIndex", page.getLastPageIndex() + 1);
        request.setAttribute("currentIndex", page.getCurrentPageIndex() + 1);
        request.setAttribute("sortBy", page.getSortBy());
        request.setAttribute("asc", page.isAsc());
        request.setAttribute("size", page.getSize());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/view/dashboard.jsp");
        requestDispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String delete = request.getParameter("selection");
        String[] deleteList = delete.split(",");
        for (String id : deleteList) {
            try {
                int computerId = Integer.parseInt(id);
                computerService.deleteComputer(computerId);
                logger.info("Computer id:{} has been deleted.", computerId);
            } catch (NumberFormatException e) {
                logger.info("Don't try to put wrong id please.");
            } catch (ServiceException e) {
                logger.error("Error in service execution.");
            }
        }
        doGet(request, response);
    }
}