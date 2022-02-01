package com.petrik.egridanielrichard_etlap;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class CategoriesController extends Controller{
    @javafx.fxml.FXML
    private Button btnAddCategory;
    @javafx.fxml.FXML
    private TableColumn<Category, String> categoryTableName;
    @javafx.fxml.FXML
    private Button btnDelCategory;
    @javafx.fxml.FXML
    private TableView<Category> categoryTable;
    @javafx.fxml.FXML
    private TableColumn<Category, Integer> categoryTableId;

    public void initialize() {
        categoryTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        categoryTableName.setCellValueFactory(new PropertyValueFactory<>("name"));

        try {
            fillTable();
        } catch (SQLException e) {
            errorAlert(e);
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void addCategoryClick(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void delCategoryClick(ActionEvent actionEvent) {
    }

    public void fillTable() throws SQLException {
        FoodDb db = new FoodDb();
        List<Category> list = db.getCategories();

        categoryTable.getItems().clear();

        for (Category c: list) {
            categoryTable.getItems().add(c);
        }
    }
}
