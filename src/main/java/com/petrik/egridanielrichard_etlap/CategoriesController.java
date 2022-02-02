package com.petrik.egridanielrichard_etlap;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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
        try {
            Controller addCategory = newWindow("add-category.fxml", "Add Category", 400, 400);
            addCategory.getStage().setOnHiding(event -> {
                try { fillTable(); } catch (SQLException e) {
                    errorAlert(e);
                    e.printStackTrace();
                }
            });

            addCategory.getStage().show();
        } catch (Exception e) {
            errorAlert(e);
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void delCategoryClick(ActionEvent actionEvent) {
        if (categoryTable.getSelectionModel().getSelectedIndex() == -1) {
            alert("Törléshez előbb válasszon ki egy sort");
            return;
        }

        Category selected = categoryTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Biztos törölni szeretné ezt a kategóriát?");
        Optional<ButtonType> result = alert.showAndWait();
        ButtonType resultType = result.orElse(ButtonType.CANCEL);

        try {
            if (resultType == ButtonType.OK) {
                if (new FoodDb().deleteCategory(selected.getId()) == 1) {
                    alert("Sikeres törlés");
                } else {
                    alert("Sikertelen törlés");
                }
                fillTable();
            }
        } catch (SQLException e) {
            errorAlert(e);
            e.printStackTrace();
        }
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
