package com.excilys.formation.cdb.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.excilys.formation.cdb.service.ComputerPage;
import com.excilys.formation.cdb.service.ComputerService;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

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
        if (!request.getParameter("next").isEmpty()) {
            page.nextPage();
        }
        if (!request.getParameter("previous").isEmpty()) {
            page.previousPage();
        }
        if (!request.getParameter("index").isEmpty()) {

        }
        session.setAttribute("page", page);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/dashboard.jsp");
        request.setAttribute("nbComputers", nbComputer);
        request.setAttribute("computer_list", page.getContent());
        requestDispatcher.forward(request, response);
    }

}