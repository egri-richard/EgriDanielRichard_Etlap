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
}
