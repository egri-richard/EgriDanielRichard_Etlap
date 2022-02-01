package com.petrik.egridanielrichard_etlap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

public class AddController extends Controller{
    @FXML
    private Button addFoodBtn;
    @FXML
    private ChoiceBox<String> cbFoodCategory;
    @FXML
    private TextArea taFoodDetails;
    @FXML
    private Spinner<Integer> spFoodPrice;
    @FXML
    private TextField tfFoodName;

    @FXML
    public void addFoodBtnClick(ActionEvent actionEvent) {
        String name = tfFoodName.getText().trim();
        if (name.isEmpty()) {
            alert("A Név mező nem lehet üres");
            return;
        }

        String details = taFoodDetails.getText().trim();
        if (details.isEmpty()) {
            alert("A Leírás mező nem lehet üres");
            return;
        }

        int price;
        try { price = spFoodPrice.getValue(); }
        catch (NullPointerException e) {
            errorAlert(e);
            e.printStackTrace();
            alert("Az ár megadása kötelező");
            return;
        }
        catch (Exception e) {
            errorAlert(e);
            e.printStackTrace();
            alert("Az ár csak 1 és 9999 közötti szám lehet");
            return;
        }

        if (cbFoodCategory.getSelectionModel().getSelectedIndex() == -1) {
            alert("Kategória kiválasztása kötelező");
            return;
        }
        String category = cbFoodCategory.getValue();

        try {
            if (new FoodDb().addFood(name, details, price, category) == 1) {
                alert("Az étel hozzáadása sikeres");
            } else {
                alert("Az étel hozzáadása sikertelen");
            }
        } catch (SQLException e) {
            errorAlert(e);
        }
    }
}
