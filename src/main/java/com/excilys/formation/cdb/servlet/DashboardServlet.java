package com.excilys.formation.cdb.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cdb.service.ComputerPage;
import com.excilys.formation.cdb.service.ComputerService;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    
    private static final long serialVersionUID = 2741128895945909738L;
    private ComputerPage page = new ComputerPage(20);


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        int nbComputer = ComputerService.INSTANCE.countAllComputers();

        if (!(request.getParameter("next") == null)) {
            page.nextPage();
        }
        if (!(request.getParameter("previous") == null)) {
            page.previousPage();
        }
        if (!(request.getParameter("index") == null)) {
            try {
                int index = Integer.parseInt(request.getParameter("index"));
                page.goToPage(index-1);
            } catch (NumberFormatException e1) {
                logger.error("Not a number:" + e1.getMessage());
            }
        }
        session.setAttribute("page", page);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/dashboard.jsp");
        request.setAttribute("nbComputers", nbComputer);
        request.setAttribute("computer_list", page.getContent());
        request.setAttribute("maxIndex", page.getLastPageIndex()+1);
        request.setAttribute("currentIndex", page.getCurrentPageIndex()+1);
        requestDispatcher.forward(request, response);
    }

}