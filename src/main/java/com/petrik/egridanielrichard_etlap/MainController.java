package com.petrik.egridanielrichard_etlap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class MainController {

    @FXML
    private Button btnDeleteFood;
    @FXML
    private TableColumn<Food, Integer> foodPriceCell;
    @FXML
    private TableColumn<Food, String> foodNameCell;
    @FXML
    private Button btnNewFood;
    @FXML
    private TableView<Food> foodTable;
    @FXML
    private TableColumn<Food, String> foodCategoryCell;

    public void initialize() {
        foodNameCell.setCellValueFactory(new PropertyValueFactory<>("name"));
        foodCategoryCell.setCellValueFactory(new PropertyValueFactory<>("category"));
        foodPriceCell.setCellValueFactory(new PropertyValueFactory<>("price"));

        try {
            fillTable();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void btnDeleteFoodClick(ActionEvent actionEvent) {
    }

    @FXML
    public void btnNewFoodClick(ActionEvent actionEvent) {
    }

    public void fillTable() throws SQLException {
        FoodDb db = new FoodDb();
        List<Food> list = db.getFoods();

        foodTable.getItems().clear();

        for (Food food: list) {
            foodTable.getItems().add(food);
        }
    }
}