package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.excilys.formation.cdb.model.Computer;

public class ComputerDB {

	Connection conn = ConnexionManager.INSTANCE.getConn();
	ArrayList<Computer> computerList = new ArrayList(); 
	
	
	public ComputerDB () throws SQLException {
			
		Statement s = conn.createStatement();
		ResultSet res = s
				.executeQuery("SELECT * FROM COMPUTER");
		
		while(res.next()) {
		    int idComputer = res.getInt( "id" );
		    String nameComputer = res.getString( "name" );
		    Date introducedComputer = res.getDate( "introduced" );
		    Date discontinuedComputer = res.getDate( "discontinued" );
		    int idCompany = res.getInt( "company_id" );
		    
		    computerList.add(new Computer(	idComputer, 
		    								nameComputer, 
		    								introducedComputer,
		    								discontinuedComputer,
		    								idCompany));
		}
	}
	
	public void create (Computer computer) throws SQLException {
		Statement s = conn.createStatement();
		s.executeUpdate( "INSERT INTO "
				+ "Computer (id, name, introduced, discontinued, company_id) "
				+ "VALUES ('"+ computer.getId() +"', '"
						+ computer.getName() +"', '"
						+ computer.getDateIntroduced() +"', '"
						+ computer.getDateDiscontinued() +"', '"
						+ computer.getIdCompany() +"', ');" );
	}
	
	public void update (Computer computer) {
		
	}
	
	public void delete (int id) throws SQLException {
		Statement s = conn.createStatement();
		s.executeUpdate("DELETE FROM Computer WHERE id='"+id+"'");
	}
 	
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub

	}
}
