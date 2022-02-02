package com.petrik.egridanielrichard_etlap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodDb {
    Connection conn;

    public FoodDb() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/etlapdb", "root", "");
    }

    public List<Category> getCategories() throws SQLException {
        List<Category> categoryList = new ArrayList<>();
        String sql = "SELECT * FROM kategoria ORDER BY id";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while(rs.next()) {
            categoryList.add(new Category(
                    rs.getInt("id"),
                    rs.getString("nev")
            ));
        }

        return categoryList;
    }

    public List<Food> getFoods() throws SQLException {
        List<Food> retList = new ArrayList<>();
        String sql = "SELECT etlap.id, etlap.nev, leiras, ar, kategoria.nev AS kategoria FROM `etlap` INNER JOIN kategoria ON etlap.kategoria_id = kategoria.id";

        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(sql);

        while(rs.next()) {
            retList.add(new Food(
                rs.getInt("id"),
                rs.getString("nev"),
                rs.getString("leiras"),
                rs.getInt("ar"),
                rs.getString("kategoria")
            ));
        }

        return retList;
    }

    public int addCategory(String name) throws SQLException {
        String sql = "INSERT INTO kategoria(nev) VALUES(?)";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, name);

        return stmt.executeUpdate();
    }

    public int deleteCategory(int id) throws SQLException {
        String sql = "DELETE FROM kategoria WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, id);

        return stmt.executeUpdate();
    }

    public int addFood(String name, String details, int price, int category) throws SQLException {
        String sql = "INSERT INTO etlap(nev, leiras, ar, kategoria_id) VALUES(?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, name);
        stmt.setString(2, details);
        stmt.setInt(3, price);
        stmt.setInt(4, category);

        return stmt.executeUpdate();
    }

    public int deleteFood(int id) throws SQLException {
        String sql = "DELETE FROM etlap WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        return stmt.executeUpdate();
    }

    public int raiseSingle(double amount, int id) throws SQLException {
        String sql;

        if (amount < 2) {
            sql = "UPDATE etlap SET ar = ar * ? WHERE id = ?";
        } else {
            sql = "UPDATE etlap SET ar = ar + ? WHERE id = ?";
        }

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setDouble(1, amount);
        stmt.setInt(2, id);
        return stmt.executeUpdate();
    }

    public int raiseAll(double amount) throws SQLException {
        String sql;

        if (amount < 2) {
            sql = "UPDATE etlap SET ar = ar * ?";
        } else {
            sql = "UPDATE etlap SET ar = ar + ?";
        }

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setDouble(1, amount);
        return stmt.executeUpdate();
    }
}
