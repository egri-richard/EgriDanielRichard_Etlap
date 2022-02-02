package com.petrik.egridanielrichard_etlap;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class AddCategoryController extends Controller {
    @javafx.fxml.FXML
    private Button btnConfirmNewCategory;
    @javafx.fxml.FXML
    private TextField tfCategoryName;

    @javafx.fxml.FXML
    public void confirmNewCategoryClick(ActionEvent actionEvent) {
        String name = tfCategoryName.getText().trim();
        if (name.isEmpty()) {
            alert("A Név mező nem lehet üres");
            return;
        }

        try {
            if (new FoodDb().addCategory(name) == 1) {
                alert("Sikeres hozzáadás");
                Stage stage = (Stage) btnConfirmNewCategory.getScene().getWindow();
                stage.close();
            } else {
                alert("Sikertelen hozzáadás");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            alert("Ezzel a névvel már szerepel kategória az adatbázisban");
            e.printStackTrace();
        } catch (SQLException e) {
            errorAlert(e);
            e.printStackTrace();
        }
    }
}
