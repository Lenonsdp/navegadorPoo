/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import connection.connectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.bean.Historico;
import model.bean.Usuario;

/**
 *
 * @author Lenon
 */
public class historicoDAO {

    Usuario usuario = new Usuario();

    public boolean create(Historico his) {

        Connection con = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT INTO historico (pagina, url, data_acesso, id_usuario) VALUES (?, ?, ?, ?)";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, his.getPagina());
            stmt.setString(2, his.getUrl());
            stmt.setObject(3, his.getData_acesso());
            stmt.setInt(4, his.getId_usuario());

            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println("Erro " + ex);
            return false;
        } finally {
            connectionFactory.closeConnection(con, stmt);
        }
    }

    public List<Historico> getAll() {
        Connection con = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Historico> his = new ArrayList<>();

        try {
            String sql = "SELECT h.data_acesso, h.pagina, h.url FROM historico AS h WHERE h.id_usuario = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, usuario.getId());
            rs = stmt.executeQuery();

            while (rs.next()) {
                Historico hist = new Historico();
                hist.setData_acesso(rs.getTimestamp("data_acesso"));
                hist.setPagina(rs.getString("pagina"));
                hist.setUrl(rs.getString("url"));
                his.add(hist);
            }
        } catch (SQLException ex) {
            System.err.println("Erro " + ex);
        } finally {
            connectionFactory.closeConnection(con, stmt, rs);
        }
        return his;
    }

    public List<Historico> getForUrl(String pagina) {
        Connection con = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Historico> his = new ArrayList<>();

        try {
            String sql = "SELECT h.data_acesso, h.pagina, h.url FROM historico AS h "
                    + "WHERE h.id_usuario = ? AND h.pagina LIKE ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, usuario.getId());
            stmt.setString(2, "%" + pagina + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Historico hist = new Historico();
                hist.setData_acesso(rs.getTimestamp("data_acesso"));
                hist.setPagina(rs.getString("pagina"));
                hist.setUrl(rs.getString("url"));
                his.add(hist);
            }
        } catch (SQLException ex) {
            System.err.println("Erro " + ex);
        } finally {
            connectionFactory.closeConnection(con, stmt, rs);
        }
        return his;
    }

    public List<Historico> getForDate(String data) {
        Connection con = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Historico> his = new ArrayList<>();

        try {
            String sql = "SELECT h.data_acesso, h.pagina, h.url FROM historico AS h "
                    + "WHERE h.id_usuario = ? AND h.data_acesso LIKE ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, usuario.getId());
            stmt.setString(2, "%" + data + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Historico hist = new Historico();
                hist.setData_acesso(rs.getTimestamp("data_acesso"));
                hist.setPagina(rs.getString("pagina"));
                hist.setUrl(rs.getString("url"));
                his.add(hist);
            }
        } catch (SQLException ex) {
            System.err.println("Erro " + ex);
        } finally {
            connectionFactory.closeConnection(con, stmt, rs);
        }
        return his;
    }

}
