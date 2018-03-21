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

@WebServlet("/db")
public class DashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 2741128895945909738L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ComputerPage page = new ComputerPage(20);
        session.setAttribute("page", page);
        
        // On récupère une connection à la base :
        try {
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/dashboard.jsp");
            request.setAttribute("computer_list", page.getContent());
            rd.forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }

    }

}