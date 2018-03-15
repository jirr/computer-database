package com.excilys.formation.cdb.persistence;

import java.sql.*;
import java.util.ArrayList;

import com.excilys.formation.cdb.mapper.ComputerMP;
import com.excilys.formation.cdb.model.Computer;

public enum ComputerDB {
	
	INSTANCE;

	static Connection conn = ConnexionManager.INSTANCE.getConn();
	
	private String selectAllRequest = "SELECT cu.id as cuId, cu.name as cuName, introduced, discontinued, ca.id as caId, ca.name as caName FROM computer cu LEFT JOIN company ca ON ca.id = cu.company_id ";
	private String createRequest 	= "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?);";
	private String updateRequest 	= "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?;";
	private String deleteRequest 	= "DELETE FROM computer WHERE id=?;";
	
	public ArrayList<Computer> list () {
		ArrayList<Computer> computerList = new ArrayList<>(); 
		try {
			Statement s = conn.createStatement();
			ResultSet res = s
					.executeQuery(selectAllRequest + ";");
			while(res.next()) {
			    computerList.add(ComputerMP.resToComputer(res));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computerList;
	}
	
	public void createComputer (Computer computer) {
		try {
			PreparedStatement ps = conn.prepareStatement(createRequest);
			ps.setString(1, computer.getName());
			ps.setDate(2, Date.valueOf(computer.getDateIntroduced()));
			ps.setDate(3, Date.valueOf(computer.getDateDiscontinued()));
			ps.setInt(4, computer.getManufactor().getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateComputer (Computer computer) {
		try {	
			PreparedStatement ps = conn.prepareStatement(updateRequest);
			ps.setString(1, computer.getName());
			ps.setDate(2, Date.valueOf(computer.getDateIntroduced()));
			ps.setDate(3, Date.valueOf(computer.getDateDiscontinued()));
			ps.setInt(4, computer.getManufactor().getId());
			ps.setInt(5, computer.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteComputer (int id) {
		try {
			PreparedStatement ps = conn.prepareStatement(deleteRequest);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Computer selectOne (int id) {
		Computer cres = null;
		ResultSet res = null;
		try {
			PreparedStatement ps = conn.prepareStatement(selectAllRequest + "WHERE cu.id = ?;");
			ps.setInt(1, id);
			res = ps.executeQuery();
			if(!res.next()) {
				return null;
			} else {
				cres = ComputerMP.resToComputer(res);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cres;
	}
}
