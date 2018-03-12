package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.excilys.formation.cdb.model.Computer;

public class ComputerDB {

	Connection conn = ConnexionManager.INSTANCE.getConn();
	ArrayList<Computer> computerList = new ArrayList<>(); 
	
	
	public void list () throws SQLException {
			
		Statement s = conn.createStatement();
		ResultSet res = s
				.executeQuery("SELECT * FROM COMPUTER");
		
		while(res.next()) {
		    int idComputer = res.getInt( "id" );
		    String nameComputer = res.getString( "name" );
		    Timestamp introducedComputer = res.getTimestamp( "introduced" );
		    Timestamp discontinuedComputer = res.getTimestamp( "discontinued" );
		    int idCompany = res.getInt( "company_id" );
		    
		    computerList.add(new Computer(	idComputer, 
		    								nameComputer, 
		    								introducedComputer,
		    								discontinuedComputer,
		    								idCompany));
		}
	}
	
	public void create (Computer computer) throws SQLException {
		PreparedStatement ps = conn.prepareStatement( "INSERT INTO "
				+ "Computer (name, introduced, discontinued, company_id) "
				+ "VALUES (?, ?, ?, ?);" );
		ps.setString(1, computer.getName());
		ps.setTimestamp(2, computer.getDateIntroduced());
		ps.setTimestamp(3, computer.getDateDiscontinued());
		ps.setInt(4, computer.getIdCompany());
		ps.executeUpdate();
	}
	
	public void update (Computer computer) throws SQLException {
		PreparedStatement ps = conn.prepareStatement( "UPDATE Computer "
				+ "SET name = ?, introduced = ?, discontinued = ?, company_id = ?"
				+ "WHERE id = ?;" );
		ps.setString(1, computer.getName());
		ps.setTimestamp(2, computer.getDateIntroduced());
		ps.setTimestamp(3, computer.getDateDiscontinued());
		ps.setInt(4, computer.getIdCompany());
		ps.setInt(5, computer.getId());
		ps.executeUpdate();
		
	}
	
	public void delete (int id) throws SQLException {
		Statement s = conn.createStatement();
		s.executeUpdate("DELETE FROM Computer WHERE id='"+id+"'");
	}
 	
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub

	}
}
