package com.petrik.egridanielrichard_etlap;

import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class AddController {
    @javafx.fxml.FXML
    private Button addFoodBtn;
    @javafx.fxml.FXML
    private ChoiceBox<String> cbFoodCategory;
    @javafx.fxml.FXML
    private TextArea taFoodDetails;
    @javafx.fxml.FXML
    private Spinner<Integer> spFoodPrice;
    @javafx.fxml.FXML
    private TextField tfFoodName;

    @javafx.fxml.FXML
    public void addFoodBtnClick(ActionEvent actionEvent) {
    }
}
