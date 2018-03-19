package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.model.Computer;

public enum ComputerDB {
    INSTANCE;

    private final String selectAllRequest = "SELECT cu.id as cuId, cu.name as cuName, introduced, discontinued, ca.id as caId, ca.name as caName FROM computer cu LEFT JOIN company ca ON ca.id = cu.company_id";
    private final String createRequest = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?);";
    private final String updateRequest = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?;";
    private final String deleteRequest = "DELETE FROM computer WHERE id=?;";

    /**
     * @return List<Computer> The list of all Computer object from the DB
     */
    public List<Computer> list() {
        List<Computer> computerList = new ArrayList<>();
        try (Connection conn = ConnexionManager.INSTANCE.getConn()) {
            Statement s = conn.createStatement();
            ResultSet res = s.executeQuery(selectAllRequest + " ;");
            while (res.next()) {
                computerList.add(ComputerMapper.INSTANCE.resToComputer(res));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return computerList;
    }

    /**
     * @param computer
     *            the computer object to create in the DB
     */
    public void createComputer(Computer computer) {
        try (Connection conn = ConnexionManager.INSTANCE.getConn()) {
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

    /**
     * @param computer
     *            the computer object to update in the DB
     */
    public void updateComputer(Computer computer) {
        try (Connection conn = ConnexionManager.INSTANCE.getConn()) {
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

    /**
     * @param id
     *            the ID of computer to delete from the DB
     */
    public void deleteComputer(int id) {
        try (Connection conn = ConnexionManager.INSTANCE.getConn()) {
            PreparedStatement ps = conn.prepareStatement(deleteRequest);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param id
     *            the ID of Computer that should exist
     * @return Optional<Computer> contains the Computer, could be empty if the id
     *         does not exist
     */
    public Optional<Computer> selectOne(int id) {
        Computer cres = null;
        ResultSet res = null;
        try (Connection conn = ConnexionManager.INSTANCE.getConn()) {
            PreparedStatement ps = conn.prepareStatement(selectAllRequest + " WHERE cu.id = ?;");
            ps.setInt(1, id);
            res = ps.executeQuery();
            if (res.next()) {
                cres = ComputerMapper.INSTANCE.resToComputer(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(cres);
    }
}