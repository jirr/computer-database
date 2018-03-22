package com.excilys.formation.cdb.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet("/addComputer")
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/addComputer.jsp");
        request.setAttribute("companyList", CompanyService.INSTANCE.listAllCompany());

        requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
        try {
            ComputerDTO computerDTO = new ComputerDTO(  request.getAttribute("computerName").toString(),
                                                        request.getAttribute("introduced").toString(),
                                                        request.getAttribute("discontinued").toString(),
                                                        Integer.parseInt(request.getAttribute("companyId").toString()));
            ComputerService.INSTANCE.createComputer(ComputerMapper.INSTANCE.dtoToComputer(computerDTO));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
}
