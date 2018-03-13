package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.excilys.formation.cdb.mapper.ComputerMP;
import com.excilys.formation.cdb.model.Computer;

public class ComputerDB {

	static Connection conn = ConnexionManager.INSTANCE.getConn();
	
	public static ArrayList<Computer> list () {
		ArrayList<Computer> computerList = new ArrayList<>(); 
		try {
			Statement s = conn.createStatement();
			ResultSet res = s
					.executeQuery("SELECT * FROM computer");
			while(res.next()) {
			    computerList.add(ComputerMP.resToComputer(res));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computerList;
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
		PreparedStatement ps = conn.prepareStatement( "UPDATE computer "
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
		s.executeUpdate("DELETE FROM computer WHERE id='"+id+"'");
	}
	
	public Computer selectOne (int id) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM computer "
				+ "WHERE id = ?;");
		ps.setInt(1, id);
		ResultSet res = ps.executeQuery();
		return ComputerMP.resToComputer(res);
	}
 	
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub

	}
}
