package com.petrik.egridanielrichard_etlap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodDb {
    Connection conn;

    public FoodDb() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/etlapdb", "root", "");
    }

    public List<Food> getFoods() throws SQLException {
        List<Food> retList = new ArrayList<>();
        String sql = "SELECT * FROM etlap";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while(rs.next()) {
            retList.add(new Food(
                rs.getInt("id"),
                rs.getString("nev"),
                rs.getString("leiras"),
                rs.getInt("ar"), rs.getString("kategoria")
            ));
        }

        return retList;
    }

    public int addFood(String name, String details, int price, String category) throws SQLException {
        String sql = "INSERT INTO etlap(nev, leiras, ar, kategoria) VALUES(?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, name);
        stmt.setString(2, details);
        stmt.setInt(3, price);
        stmt.setString(4, category);

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
