package com.petrik.egridanielrichard_etlap;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MainController extends Controller {

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
    @FXML
    private Spinner<Integer> spAmountOfFixRaise;
    @FXML
    private Spinner<Integer> spAmountOfPercentRaise;
    @FXML
    private Button btnRaisePercent;
    @FXML
    private Button btnRaiseFix;
    @FXML
    private Button btnShowCategories;
    @FXML
    private ChoiceBox<Category> cbCategorySort;
    @FXML
    private Label testText;

    public void initialize() {
        foodNameCell.setCellValueFactory(new PropertyValueFactory<>("name"));
        foodCategoryCell.setCellValueFactory(new PropertyValueFactory<>("category"));
        foodPriceCell.setCellValueFactory(new PropertyValueFactory<>("price"));

        try {
            List<Category> cg = new FoodDb().getCategories();
            for (Category c: cg) {
                cbCategorySort.getItems().add(c);
            }
            Category all = new Category(0,"összes");
            cbCategorySort.getItems().add(all);
            fillTable();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        cbCategorySort.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Category>() {
            @Override
            public void changed(ObservableValue<? extends Category> observableValue, Category category, Category t1) {
                try {
                    fillTable(t1);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    public void btnDeleteFoodClick(ActionEvent actionEvent) {
        if (foodTable.getSelectionModel().getSelectedIndex() == -1) {
            alert("Törléshez előbb válasszon ki egy sort");
            return;
        }

        Food selected = foodTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Biztos törölni szeretné ezt az ételt?");
        Optional<ButtonType> result = alert.showAndWait();
        ButtonType resultType = result.orElse(ButtonType.CANCEL);

        try {
            if (resultType == ButtonType.OK) {
                if (new FoodDb().deleteFood(selected.getId()) == 1) {
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

    @FXML
    public void btnNewFoodClick(ActionEvent actionEvent) {
        try {
            Controller addWindow = newWindow("add-food-view.fxml", "Add Food", 400, 400);
            addWindow.getStage().setOnCloseRequest(event -> {
                try { fillTable(); } catch (SQLException e) {
                    errorAlert(e);
                    e.printStackTrace();
                }
            });

            addWindow.getStage().show();
        } catch (Exception e) {
            errorAlert(e);
            e.printStackTrace();
        }

    }

    public void fillTable() throws SQLException {
        FoodDb db = new FoodDb();
        List<Food> list = db.getFoods();

        foodTable.getItems().clear();

        for (Food food : list) {
            foodTable.getItems().add(food);
        }
    }

    public void fillTable(Category sortBy) throws SQLException {
        FoodDb db = new FoodDb();
        List<Food> list = db.getFoods();

        foodTable.getItems().clear();

        if (sortBy.getName().equals("összes")) {
            for (Food food: list) {
                foodTable.getItems().add(food);
            }
        } else {
            for (Food food: list) {
                if (food.getCategory().equals(sortBy.getName())) {
                    foodTable.getItems().add(food);
                }
            }
        }
    }

    @FXML
    public void RaiseFixClick(ActionEvent actionEvent) {
        double amount = spAmountOfFixRaise.getValue();

        if (foodTable.getSelectionModel().getSelectedIndex() == -1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Biztos szeretné növelni az összes étel árát?");
            Optional<ButtonType> result = alert.showAndWait();
            ButtonType resultType = result.orElse(ButtonType.CANCEL);

            if (resultType == ButtonType.OK) {
                try {
                    if (new FoodDb().raiseAll(amount) == -1) {
                        alert("Sikertelen növelés");
                    } else {
                        alert("Sikeres növelés");
                    }
                } catch (SQLException e) {
                    errorAlert(e);
                    e.printStackTrace();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            String name = foodTable.getSelectionModel().getSelectedItem().getName();
            alert.setHeaderText("Biztos szeretné növelni "+name+" árát?");
            Optional<ButtonType> result = alert.showAndWait();
            ButtonType resultType = result.orElse(ButtonType.CANCEL);

            if (resultType == ButtonType.OK) {
                int id = foodTable.getSelectionModel().getSelectedItem().getId();

                try {
                    if (new FoodDb().raiseSingle(amount, id) == -1) {
                        alert("Sikertelen növelés");
                    } else {
                        alert("Sikeres növelés");
                    }
                } catch (SQLException e) {
                    errorAlert(e);
                    e.printStackTrace();
                }
            }
        }

        try {
            fillTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void RaisePercentClick(ActionEvent actionEvent) {
        double amount = 1 + spAmountOfPercentRaise.getValue() / 100d;

        if (foodTable.getSelectionModel().getSelectedIndex() == -1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Biztos szeretné növelni az összes étel árát?");
            Optional<ButtonType> result = alert.showAndWait();
            ButtonType resultType = result.orElse(ButtonType.CANCEL);

            if (resultType == ButtonType.OK) {
                try {
                    if (new FoodDb().raiseAll(amount) == -1) {
                        alert("Sikertelen növelés");
                    } else {
                        alert("Sikeres növelés");
                    }
                } catch (SQLException e) {
                    errorAlert(e);
                    e.printStackTrace();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            String name = foodTable.getSelectionModel().getSelectedItem().getName();
            alert.setHeaderText("Biztos szeretné növelni "+name+" árát?");
            Optional<ButtonType> result = alert.showAndWait();
            ButtonType resultType = result.orElse(ButtonType.CANCEL);

            if (resultType == ButtonType.OK) {
                int id = foodTable.getSelectionModel().getSelectedItem().getId();

                try {
                    if (new FoodDb().raiseSingle(amount, id) == -1) {
                        alert("Sikertelen növelés");
                    } else {
                        alert("Sikeres növelés");
                    }
                } catch (SQLException e) {
                    errorAlert(e);
                    e.printStackTrace();
                }
            }
        }

        try {
            fillTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showCategoriesClick(ActionEvent actionEvent) {
        try {
            Controller categoryWindow = newWindow("show-categories.fxml", "Categories", 210, 300);
            categoryWindow.getStage().setOnCloseRequest(event -> {
                try { fillTable(); } catch (SQLException e) {
                    errorAlert(e);
                    e.printStackTrace();
                }
            });

            categoryWindow.getStage().show();
        } catch (Exception e) {
            errorAlert(e);
            e.printStackTrace();
        }
    }
}